package lk.ijse.salongeetha.model.castom.impl;

import lk.ijse.salongeetha.db.DBConnection;
import lk.ijse.salongeetha.model.CrudUtil;
import lk.ijse.salongeetha.to.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerModel {
    public static boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.setQuery("INSERT INTO Customer VALUES (?,?,?,?,?,?)", customer.getNic(), customer.getName(),
                customer.getPhoneNumber(), customer.getEmail(), customer.getDob(), customer.getUserName());
    }

    public static boolean deleteCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.setQuery("DELETE FROM Customer WHERE NIC=?", customer.getNic());
    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.setQuery("UPDATE Customer SET Name=?,Phone_number=?,Email=?,DOB=? WHERE NIC=?", customer.getName(),
                customer.getPhoneNumber(), customer.getEmail(), customer.getDob(), customer.getNic());
    }

    public static ArrayList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet resultSet = CrudUtil.setQuery("SELECT * FROM Customer");
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setNic(String.valueOf(resultSet.getObject(1)));
            customer.setName(String.valueOf(resultSet.getObject(2)));
            customer.setPhoneNumber(String.valueOf(resultSet.getObject(3)));
            customer.setEmail(String.valueOf(resultSet.getObject(4)));
            customer.setDob(String.valueOf(resultSet.getObject(5)));
            customers.add(customer);
        }
        return customers;
    }

    public static ArrayList<Customer> searchCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        String setColumn;
        Pattern userNamePattern = Pattern.compile("[a-zA-Z]{1,}");
        Matcher matcher = userNamePattern.matcher(customer.getName());
        if (matcher.matches()) {
            setColumn = "SELECT * FROM Customer WHERE Name LIKE ?";
        } else {
            setColumn = "SELECT * FROM Customer WHERE NIC LIKE ?";
        }
        ResultSet resultSet = CrudUtil.setQuery(setColumn, "%" + customer.getName() + "%");
        while (resultSet.next()) {
            Customer searchCustomer = new Customer();
            searchCustomer.setNic(String.valueOf(resultSet.getObject(1)));
//            System.out.println("data awo"+String.valueOf(resultSet.getObject(1)));
            searchCustomer.setName(String.valueOf(resultSet.getObject(2)));
            searchCustomer.setPhoneNumber(String.valueOf(resultSet.getObject(3)));
            searchCustomer.setEmail(String.valueOf(resultSet.getObject(4)));
            searchCustomer.setDob(String.valueOf(resultSet.getObject(5)));
            customers.add(searchCustomer);
        }
        return customers;
    }

    public static ArrayList<Customer> searchCustomerDetails(Customer customer) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = new ArrayList<>();
        ResultSet resultSet = CrudUtil.setQuery("SELECT * FROM Customer WHERE NIC=?", customer.getNic());
        while (resultSet.next()) {
            Customer searchCustomer = new Customer();
            searchCustomer.setNic(String.valueOf(resultSet.getObject(1)));
            searchCustomer.setName(String.valueOf(resultSet.getObject(2)));
            searchCustomer.setPhoneNumber(String.valueOf(resultSet.getObject(3)));
            searchCustomer.setEmail(String.valueOf(resultSet.getObject(4)));
            searchCustomer.setDob(String.valueOf(resultSet.getObject(5)));
            customers.add(searchCustomer);
        }
        return customers;
    }
}
