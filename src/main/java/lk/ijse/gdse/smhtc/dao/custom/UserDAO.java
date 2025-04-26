package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.dao.CrudDAO;
import lk.ijse.gdse.smhtc.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface UserDAO extends CrudDAO<User> {
    //public String getNextId();
    //public boolean save(User entity);
    //public boolean update(User entity);
    //public boolean delete(String pk);
    //public List<User> getAll();
    //public Optional<User> findById(String pk);
    public boolean updatePasswordByUsername(String username, String newPassword);
    //public Optional<String> getLastPK();

    public Optional<String> checkLogin(String userName, String password);
}
