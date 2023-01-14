package lk.ijse.salongeetha.bo.castom.impl;

import lk.ijse.salongeetha.bo.castom.CreateNewPasswordBO;
import lk.ijse.salongeetha.dao.castom.LoginDAO;
import lk.ijse.salongeetha.dao.castom.impl.LoginDAOImpl;
import lk.ijse.salongeetha.to.User;

import java.sql.SQLException;

public class CreateNewPasswordBOImpl implements CreateNewPasswordBO {
    LoginDAO loginDAO = new LoginDAOImpl();

    @Override
    public boolean changePassword(User user) throws SQLException, ClassNotFoundException {
        return loginDAO.ChangePassword(user);
    }
}
