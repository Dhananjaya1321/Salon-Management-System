package lk.ijse.salongeetha.bo.castom;

import lk.ijse.salongeetha.bo.SuperBOImpl;
import lk.ijse.salongeetha.to.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CustomerBO extends SuperBOImpl {
    boolean deleteCustomer(Customer customer) throws SQLException, ClassNotFoundException;

    ResultSet searchCustomer(boolean value, Customer customer) throws SQLException, ClassNotFoundException;

    ResultSet getAllCustomer() throws SQLException, ClassNotFoundException;
}
