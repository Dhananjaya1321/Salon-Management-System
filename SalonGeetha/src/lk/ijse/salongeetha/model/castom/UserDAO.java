package lk.ijse.salongeetha.dao.castom;

import lk.ijse.salongeetha.dao.SQLUtil;
import lk.ijse.salongeetha.to.Employee;
import lk.ijse.salongeetha.to.User;

import java.sql.SQLException;

public interface UserDAO extends SQLUtil<User> {
    boolean delete(User user, Employee employee) throws SQLException, ClassNotFoundException;
}
