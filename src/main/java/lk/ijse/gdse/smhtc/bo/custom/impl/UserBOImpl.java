package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.UserBO;
import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.UserDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.UserDAOImpl;
import org.hibernate.Session;

import java.util.Optional;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = new UserDAOImpl();

    @Override
    public Optional<String> checklogin(String userName, String password) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Optional<String> role = Optional.empty();

        try {
            session.beginTransaction();

            role = userDAO.checkLogin(userName, password);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return role;
    }


//    public Optional<String> checklogin(String userName, String password) {
//        return userDAO.checkLogin(userName, password);
//    }

}
