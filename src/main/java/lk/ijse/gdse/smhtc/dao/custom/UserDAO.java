package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    public String getNextId();
    public boolean save(User entity);
    public boolean update(User entity);
    public boolean delete(String pk);
    public List<User> getAll();
    public Optional<User> findById(String pk);
    public Optional<String> getLastPK();

    public Optional<String> checkLogin(String userName, String password);
}
