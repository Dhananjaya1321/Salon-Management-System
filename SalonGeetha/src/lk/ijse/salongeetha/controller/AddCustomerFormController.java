package lk.ijse.salongeetha.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.salongeetha.bo.BOImplTypes;
import lk.ijse.salongeetha.bo.FactoryBOImpl;
import lk.ijse.salongeetha.bo.castom.AddCustomerBO;
import lk.ijse.salongeetha.to.CustomerDTO;
import lk.ijse.salongeetha.util.Validation;
import lk.ijse.salongeetha.util.ValidityCheck;

import java.sql.SQLException;
import java.util.Optional;

import static javafx.scene.paint.Color.RED;

public class AddCustomerFormController {
    public AnchorPane popUpPane;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtNic;
    public JFXTextField txtPhone;
    public JFXDatePicker txtDOB;
    public static String userName;
    public Label lblNameValidation;
    public Label lblEmailValidation;
    public Label lblPhoneValidation;
    public Label lblNICValidation;
    private boolean reload = false;
    AddCustomerBO customerBO = (AddCustomerBO) FactoryBOImpl.getFactoryBO().setBO(BOImplTypes.ADD_CUSTOMER);

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        String name = txtName.getText();
        String nic = txtNic.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String dob = String.valueOf(txtDOB.getValue());
        CustomerDTO customerDTO = new CustomerDTO(nic, name, phone, email, dob, userName);

        if (ValidityCheck.check(Validation.NAME, name)) {
            if (ValidityCheck.check(Validation.NIC, nic)) {
                if (ValidityCheck.check(Validation.PHONE, phone)) {
                    if (ValidityCheck.check(Validation.EMAIL, email)) {
                        addCustomer(customerDTO);
                        closeCustomerAddForm(actionEvent);
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
                txtNic.requestFocus();
                txtNic.setFocusColor(RED);
                lblNICValidation.setText("Invalid NIC");
            }
        } else {
            txtName.requestFocus();
            txtName.setFocusColor(RED);
            lblNameValidation.setText("Invalid name");
        }


    }

    private void addCustomer(CustomerDTO customerDTO) {
        try {
            boolean addCustomer = customerBO.addCustomer(customerDTO);
            if (addCustomer) {
                ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Save is successful", ok);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(no) == ok) {

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        closeCustomerAddForm(actionEvent);
    }

    public void initialize() {
        popUpPane.setVisible(true);

    }

    private void closeCustomerAddForm(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        this.reload = true;
    }
}
