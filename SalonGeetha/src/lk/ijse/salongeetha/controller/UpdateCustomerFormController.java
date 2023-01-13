package lk.ijse.salongeetha.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salongeetha.dao.castom.CustomerDAO;
import lk.ijse.salongeetha.dao.castom.impl.CustomerModel;
import lk.ijse.salongeetha.to.Customer;
import lk.ijse.salongeetha.util.Validation;
import lk.ijse.salongeetha.util.ValidityCheck;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static javafx.scene.paint.Color.RED;

public class UpdateCustomerFormController {

    public Label lblNic;
    public Label lblNameValidation;
    public Label lblEmailValidation;
    public Label lblPhoneValidation;
    @FXML
    private AnchorPane popUpPane;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private JFXDatePicker txtDOB;
    private static Customer customer;
    CustomerDAO customerDAO=new CustomerModel();
    public static void getUpdateDetails(Customer customer){
        UpdateCustomerFormController.customer =customer;
    }
    @FXML
    void btnCloseOnAction(ActionEvent event) {
        closeCustomerAddForm(event);
    }

    @FXML
    void btnSaveCustomerOnAction(ActionEvent event) {
        String nic = lblNic.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String dob = String.valueOf(txtDOB.getValue());
        customer=new Customer(nic,name,phone,email,dob);


        if (ValidityCheck.check(Validation.NAME, name)) {
                if (ValidityCheck.check(Validation.PHONE, phone)) {
                    if (ValidityCheck.check(Validation.EMAIL, email)) {


                        try {
                            boolean updateCustomer = customerDAO.update(customer);
                            if (updateCustomer){
                                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Save is successful", ok);
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.orElse(no) == ok) {
                                    closeCustomerAddForm(event);

                                }
                            }

                        } catch (SQLException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }


                    } else {
                        txtEmail.requestFocus();
                        txtEmail.setFocusColor(RED);
                        lblEmailValidation.setText("Invalid email");
                    }
                } else {
                    txtPhone.requestFocus();
                    txtPhone.setFocusColor(RED);
                    lblPhoneValidation.setText("Invalid number");
                }
        } else {
            txtName.requestFocus();
            txtName.setFocusColor(RED);
            lblNameValidation.setText("Invalid name");
        }
    }
    private void closeCustomerAddForm(ActionEvent actionEvent){
        Node node = (Node) actionEvent.getSource();
        Stage stage= (Stage) node.getScene().getWindow();
        stage.close();
    }
    public void initialize(){
        lblNic.setText(customer.getNic());
        txtName.setText(customer.getName());
        txtEmail.setText(customer.getEmail());
        txtPhone.setText(customer.getPhoneNumber());
        txtDOB.setValue(LocalDate.parse(customer.getDob()));
    }

}
