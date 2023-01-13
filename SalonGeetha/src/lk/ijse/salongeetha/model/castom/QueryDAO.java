package lk.ijse.salongeetha.model.castom;

import lk.ijse.salongeetha.to.BookRentalsDetail;
import lk.ijse.salongeetha.to.ServiceAppointmentDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryDAO {
    ResultSet getAmountDueServiceAppointmentDetails(ServiceAppointmentDetail serviceAppointmentDetail) throws SQLException, ClassNotFoundException;

    ResultSet getAllEmployeeServiceDetails() throws SQLException, ClassNotFoundException;

    ResultSet getAmountDueBookRentalsDetail(BookRentalsDetail bookRentalsDetail) throws SQLException, ClassNotFoundException;
}
