package lk.ijse.salongeetha.dao.castom;

import lk.ijse.salongeetha.dao.SQLUtil;
import lk.ijse.salongeetha.entity.Employee;
import lk.ijse.salongeetha.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeDAO extends SQLUtil<Employee> {
    ArrayList<Employee> getBeauticians() throws SQLException, ClassNotFoundException;

    ArrayList<Employee> searchEmployeeDetails(Employee employee) throws SQLException, ClassNotFoundException;

    boolean addReceptionist(Employee employee, User user) throws SQLException, ClassNotFoundException;


    Employee checkAdmin() throws SQLException, ClassNotFoundException;

    boolean updateAdmin(Employee employee) throws SQLException, ClassNotFoundException;
}
