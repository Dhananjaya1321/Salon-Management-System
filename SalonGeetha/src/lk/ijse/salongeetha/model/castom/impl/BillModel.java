package lk.ijse.salongeetha.model.castom.impl;

import lk.ijse.salongeetha.db.DBConnection;
import lk.ijse.salongeetha.to.BillPayment;
import lk.ijse.salongeetha.to.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BillModel {
    public static boolean addBillPaymentDetails(BillPayment billPayment) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getDBConnection().getConnection().
                prepareStatement("INSERT INTO Bill_payment VALUES (?,?,?,?,?,?)");
        preparedStatement.setObject(1, billPayment.getBilId());
        preparedStatement.setObject(2, billPayment.getDate());
        preparedStatement.setObject(3, billPayment.getDescription());
        preparedStatement.setObject(4, billPayment.getTitle());
        preparedStatement.setObject(5, billPayment.getAmountPaid());
        preparedStatement.setObject(6, billPayment.getEmpId());//mulinma employee add karala inna employee kenekge id eka thoranna one
        boolean isAdded = preparedStatement.executeUpdate() > 0;
        if (isAdded) {
            return true;
        }
        return false;
    }

    public static String checkId() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getDBConnection().getConnection()
                .prepareStatement("SELECT Bil_Id FROM bill_payment ORDER BY Bil_Id DESC LIMIT 1");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return String.valueOf(resultSet.getObject(1));

        }
        return null;
    }

    public static ArrayList<BillPayment> getAllBillPayments() throws SQLException, ClassNotFoundException {
        ArrayList<BillPayment> billPayments = new ArrayList<>();
        PreparedStatement preparedStatement = DBConnection.getDBConnection().getConnection()
                .prepareStatement("SELECT * FROM bill_payment");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
           BillPayment billPayment=new BillPayment();
            billPayment.setBilId(String.valueOf(resultSet.getObject(1)));
            billPayment.setDate(String.valueOf(resultSet.getObject(2)));
            billPayment.setDescription(String.valueOf(resultSet.getObject(3)));
            billPayment.setTitle(String.valueOf(resultSet.getObject(4)));
            billPayment.setAmountPaid((Double) resultSet.getObject(5));
            billPayment.setEmpId(String.valueOf(resultSet.getObject(6)));
            billPayments.add(billPayment);
        }
        return billPayments;
    }

    public static boolean deleteBillPayment(BillPayment billPayment) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getDBConnection().getConnection()
                .prepareStatement("DELETE FROM bill_payment WHERE Bil_Id=?");
        preparedStatement.setObject(1, billPayment.getBilId());
        int executeUpdate = preparedStatement.executeUpdate();
        if (executeUpdate > 0) {
            return true;
        }
        return false;
    }

    public static boolean updateBillPayment(BillPayment billPayment) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getDBConnection().getConnection()
                .prepareStatement("UPDATE Bill_payment SET Date=?,Description=?,Title=?,Amount_paid=?,Emp_Id=? WHERE Bil_Id=?");
        preparedStatement.setObject(1,billPayment.getDate());
        preparedStatement.setObject(2,billPayment.getDescription());
        preparedStatement.setObject(3,billPayment.getTitle());
        preparedStatement.setObject(4,billPayment.getAmountPaid());
        preparedStatement.setObject(5,billPayment.getEmpId());
        preparedStatement.setObject(6,billPayment.getBilId());
        int executeUpdate = preparedStatement.executeUpdate();
        if(executeUpdate > 0){
            return true;
        }
        return false;
    }

    public static ArrayList<BillPayment> searchBill(BillPayment billPayment) throws SQLException, ClassNotFoundException {
        ArrayList<BillPayment> billPayments=new ArrayList<>();
        String setColumn;
        Pattern userNamePattern = Pattern.compile("[a-zA-Z]{1,}");
        Matcher matcher = userNamePattern.matcher(billPayment.getTitle());
        if (matcher.matches()) {
            setColumn="SELECT * FROM Bill_payment WHERE Title LIKE ?";
        }else {
            setColumn="SELECT * FROM Bill_payment WHERE Bil_Id LIKE ?";
        }
        PreparedStatement prepareStatement = DBConnection.getDBConnection().getConnection().prepareStatement(setColumn);
        prepareStatement.setObject(1,"%"+billPayment.getTitle()+"%");
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            BillPayment searchBillPayment=new BillPayment();
            searchBillPayment.setBilId(String.valueOf(resultSet.getObject(1)));
            searchBillPayment.setDate(String.valueOf(resultSet.getObject(2)));
            searchBillPayment.setDescription(String.valueOf(resultSet.getObject(3)));
            searchBillPayment.setTitle(String.valueOf(resultSet.getObject(4)));
            searchBillPayment.setAmountPaid((Double) resultSet.getObject(5));
            searchBillPayment.setEmpId(String.valueOf(resultSet.getObject(6)));
            billPayments.add(searchBillPayment);
        }
        return billPayments;
    }
}