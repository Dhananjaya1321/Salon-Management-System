package lk.ijse.salongeetha.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import lk.ijse.salongeetha.bo.BOImplTypes;
import lk.ijse.salongeetha.bo.FactoryBOImpl;
import lk.ijse.salongeetha.bo.castom.ProductBO;
import lk.ijse.salongeetha.dao.CrudUtil;
import lk.ijse.salongeetha.to.ProductDTO;
import lk.ijse.salongeetha.to.SupplierDTO;
import lk.ijse.salongeetha.to.tm.ProductTM;
import lk.ijse.salongeetha.util.GenerateId;
import lk.ijse.salongeetha.util.IdTypes;
import lk.ijse.salongeetha.util.Validation;
import lk.ijse.salongeetha.util.ValidityCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.RED;

public class ManageProductController {


    public JFXTextField txtProductId;
    public TableColumn columenDelete;
    public TableColumn columenUpdate;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public TextField txtSearch;
    public Label lblVBrand;
    public Label lblVPrice;
    public Label lblVQty;
    @FXML
    private GridPane rigthPane;

    @FXML
    private Label lblBar;

    @FXML
    private TableView<ProductTM> tblView;

    @FXML
    private TableColumn<?, ?> columenProductId;

    @FXML
    private TableColumn<?, ?> columenCategory;

    @FXML
    private TableColumn<?, ?> columenSupplierId;

    @FXML
    private TableColumn<?, ?> columenBrand;

    @FXML
    private TableColumn<?, ?> columenQty;

    @FXML
    private TableColumn<?, ?> columenUnitPrice;

    @FXML
    private TableColumn<?, ?> columenDescription;

    @FXML
    private TableColumn<?, ?> columenAction;

    @FXML
    private Label lblProduct;

    @FXML
    private JFXComboBox<String> cmbProductCatogary;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private JFXTextField txtBrand;

    @FXML
    private JFXTextField txtUnitPrice;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtQtyOnHand;
    ArrayList<ProductDTO> productDTOArrayList;
    ProductBO productBO = (ProductBO) FactoryBOImpl.getFactoryBO().setBO(BOImplTypes.PRODUCT);

    {
        try {
            productDTOArrayList = getAllProduct();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    ObservableList<ProductTM> observableList = FXCollections.observableArrayList();

    private void loadAllData() {
        for (ProductDTO p : productDTOArrayList) {
            JFXButton delete = new JFXButton("Delete");
            JFXButton update = new JFXButton("Update");
            update.setStyle("-fx-background-color: linear-gradient(to right, #17ff00, #12fe18, #0bfc25, #05fb2e, #00f936)");
            delete.setStyle("-fx-background-color: linear-gradient(to right, #fb0000, #fc0000, #fd0000, #fe0000, #ff0000)");
            delete.setOnAction((e) -> {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(no) == ok) {
                    ProductTM tm = tblView.getSelectionModel().getSelectedItem();

                    String productId = tm.getProId();
                    ProductDTO productDTO = new ProductDTO();

                    productDTO.setProId(productId);
                    try {
                        boolean isDeleted = productBO.deleteProduct(productDTO);
                        if (isDeleted) {
                            Alert alert1 = new Alert(Alert.AlertType.WARNING, "ProductDTO delete successfully");
                            alert1.show();
                            setNextId();

                        }

                    } catch (SQLException | ClassNotFoundException exception) {
                        throw new RuntimeException(exception);
                    }


                    tblView.getItems().removeAll(tm);
                }

            });
            update.setOnAction((e) -> {
                ProductTM tm = tblView.getSelectionModel().getSelectedItem();
                btnUpdate.setVisible(true);
                btnAdd.setVisible(false);

                lblProduct.setText(tm.getProId());
                cmbProductCatogary.setValue(tm.getCategory());
                txtDescription.setText(tm.getDescription());
                txtBrand.setText(String.valueOf(tm.getBrand()));
                txtUnitPrice.setText(String.valueOf(tm.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(tm.getQtyOnHand()));
                cmbSupplierId.setValue(String.valueOf(tm.getSupId()));

            });
            ProductTM productTM = new ProductTM(p.getProId(), p.getDescription(), p.getCategory(), p.getBrand(), p.getSupId(), p.getUnitPrice(), p.getQtyOnHand(), delete, update);
            observableList.add(productTM);
            tblView.setItems(observableList);
        }
    }

    @FXML
    void btnAddONAction(ActionEvent event) {
        String productId = lblProduct.getText();
        String catogary = cmbProductCatogary.getValue();
        String supplierId = cmbSupplierId.getValue();
        String brand = txtBrand.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String description = txtDescription.getText();
        if (ValidityCheck.check(Validation.NAME, brand)) {
            if (qtyOnHand >= 0) {
                if (unitPrice >= 0) {
                    ProductDTO productDTO = new ProductDTO(productId, description, catogary, brand, supplierId, unitPrice, qtyOnHand);
                    try {
                        boolean addProduct = productBO.addProduct(productDTO);
                        if (addProduct) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "ProductDTO collection is successful");
                            alert.show();
                            setNextId();
                            cleanAll();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
                            alert.show();
                        }
                        tblView.getItems().clear();
                        productDTOArrayList = getAllProduct();
                        loadAllData();
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    txtUnitPrice.setFocusColor(RED);
                    txtUnitPrice.requestFocus();
                    lblVPrice.setText("Invalid price");
                }
            } else {
                txtQtyOnHand.setFocusColor(RED);
                txtQtyOnHand.requestFocus();
                lblVQty.setText("Invalid Qty");
            }
        } else {
            txtBrand.setFocusColor(RED);
            txtBrand.requestFocus();
            lblVBrand.setText("Invalid brand");
        }
    }

    public void btnUpdateONAction(ActionEvent actionEvent) {
        String productId = lblProduct.getText();
        String catogary = cmbProductCatogary.getValue();
        String supplierId = cmbSupplierId.getValue();
        String brand = txtBrand.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String description = txtDescription.getText();
        if (ValidityCheck.check(Validation.NAME, brand)) {
            if (qtyOnHand >= 0) {
                if (unitPrice >= 0) {
                    ProductDTO productDTO = new ProductDTO(productId, description, catogary, brand, supplierId, unitPrice, qtyOnHand);
                    try {
                        boolean updateProduct = productBO.updateProduct(productDTO);
                        if (updateProduct) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful");
                            alert.show();
                            setNextId();
                            cleanAll();
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    txtUnitPrice.setFocusColor(RED);
                    txtUnitPrice.requestFocus();
                    lblVPrice.setText("Invalid price");
                }
            } else {
                txtQtyOnHand.setFocusColor(RED);
                txtQtyOnHand.requestFocus();
                lblVQty.setText("Invalid Qty");
            }
        } else {
            txtBrand.setFocusColor(RED);
            txtBrand.requestFocus();
            lblVBrand.setText("Invalid brand");
        }
    }

    public void btnCleanONAction(ActionEvent actionEvent) {
        cleanAll();
    }

    private void cleanAll() {
        txtDescription.setText("");
        txtBrand.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
        txtSearch.setText("");
        cmbSupplierId.setValue("");
        cmbProductCatogary.setValue("");
        btnUpdate.setVisible(false);
        btnAdd.setVisible(true);
        lblVPrice.setText(null);
        lblVBrand.setText(null);
        lblVQty.setText(null);
        try {
            tblView.getItems().clear();
            productDTOArrayList = getAllProduct();
            loadAllData();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setNextId() {
        try {

            String currentId = productBO.checkIdProduct();
            String generateNextId = GenerateId.generateNextId(currentId, IdTypes.PRODUCT);
            lblProduct.setText(generateNextId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        setNextId();
        columenProductId.setCellValueFactory(new PropertyValueFactory<>("proId"));
        columenCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columenSupplierId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        columenBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        columenQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        columenUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        columenDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columenDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
        columenUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        cleanAll();

        String[] category = {"Shampoo", "Conditioner", "Leave in Deep conditioner", "Styling Products", "Hair spray", "Clay", "Wax", "Brushes", "Other"};
        cmbProductCatogary.getItems().addAll(category);

        try {
            ArrayList<SupplierDTO> supplierDTOIds = getAllSupplier();
            String[] ids;
            if (supplierDTOIds.size() != 0) {
                ids = new String[supplierDTOIds.size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = String.valueOf(supplierDTOIds.get(i).getSupId());
                }
                cmbSupplierId.getItems().addAll(ids);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        search();
    }

    private ProductDTO productDTO = new ProductDTO();

    public void txtSearchOnAction(KeyEvent keyEvent) {
        search();
    }

    private void search() {
        String text = txtSearch.getText();
        if (!text.equals("")) {
            cleanTable();
            productDTO.setBrand(text);
            try {
                productDTOArrayList = search(productDTO);
                loadAllData();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            cleanTable();
            loadAllData();
        }
    }

    public ArrayList<ProductDTO> search(ProductDTO productDTO) throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        Pattern userNamePattern = Pattern.compile("[a-zA-Z]{1,}");
        Matcher matcher = userNamePattern.matcher(productDTO.getBrand());
        ResultSet resultSet = productBO.searchProduct(matcher.matches(), productDTO);
        while (resultSet.next()) {
            ProductDTO searchProductDTO = new ProductDTO();
            searchProductDTO.setProId(String.valueOf(resultSet.getObject(1)));
            searchProductDTO.setDescription(String.valueOf(resultSet.getObject(2)));
            searchProductDTO.setCategory(String.valueOf(resultSet.getObject(3)));
            searchProductDTO.setBrand(String.valueOf(resultSet.getObject(4)));
            searchProductDTO.setUnitPrice((Double) resultSet.getObject(5));
            searchProductDTO.setQtyOnHand((Integer) resultSet.getObject(6));
            searchProductDTO.setSupId(String.valueOf(resultSet.getObject(7)));
            productDTOS.add(searchProductDTO);
        }
        return productDTOS;
    }


    public void cleanTable() {
        try {
            tblView.getItems().clear();
            productDTOArrayList = getAllProduct();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<ProductDTO> getAllProduct() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        ResultSet resultSet = productBO.getAllProduct();
        while (resultSet.next()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProId(String.valueOf(resultSet.getObject(1)));
            productDTO.setDescription(String.valueOf(resultSet.getObject(2)));
            productDTO.setCategory(String.valueOf(resultSet.getObject(3)));
            productDTO.setBrand(String.valueOf(resultSet.getObject(4)));
            productDTO.setUnitPrice((Double) resultSet.getObject(5));
            productDTO.setQtyOnHand((Integer) resultSet.getObject(6));
            productDTO.setSupId(String.valueOf(resultSet.getObject(7)));
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    private ArrayList<SupplierDTO> getAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = productBO.getAllSupplier();
        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();
        while (resultSet.next()) {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setSupId(String.valueOf(resultSet.getObject(1)));
            supplierDTO.setDescription(String.valueOf(resultSet.getObject(2)));
            supplierDTO.setName(String.valueOf(resultSet.getObject(3)));
            supplierDTO.setAddress(String.valueOf(resultSet.getObject(4)));
            supplierDTO.setPhoneNumber(String.valueOf(resultSet.getObject(5)));
            supplierDTO.setEmail(String.valueOf(resultSet.getObject(6)));
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }
}

