package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.UserDAO;
import lk.ijse.gdse.smhtc.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        Optional<String> lastPkOptional = getLastPK();

        if (lastPkOptional.isPresent()) {
            String lastPk = lastPkOptional.get(); // Ex: "U005"
            int numberPart = Integer.parseInt(lastPk.substring(1)); // Remove 'U'
            int nextNumber = numberPart + 1;
            return String.format("U%03d", nextNumber); // Ex: "U006"
        }

        return "U001"; // If no previous ID exists
    }
    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery("SELECT u.id FROM User u ORDER BY u.id DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();

        return Optional.ofNullable(lastPk);
    }

    @Override
    public boolean save(User entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean update(User entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity);
            transaction.commit();
            return true;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }    }

    @Override
    public boolean delete(String pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            System.out.println("Finding user by ID: " + pk);
            User user = session.find(User.class, pk);
            if (user != null) {
                System.out.println("User found, proceeding to delete.");
                session.remove(user);
                transaction.commit();
                System.out.println("User deleted.");
                return true;
            } else {
                System.out.println("User not found, cannot delete.");
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        Session session = factoryConfiguration.getSession();
        List<User> User = session.createQuery("FROM User", User.class).list();
        session.close();
        return User;

    }

    @Override
    public Optional<User> findById(String pk) {
        Session session = factoryConfiguration.getSession();
        User user = session.get(User.class, pk);
        session.close();
//        if (customer == null){
//            return Optional.empty();
//        }
//        return Optional.of(customer);
        return Optional.ofNullable(user);

    }

    @Override
    public Optional<String> checkLogin(String userName, String password) {
        try (Session session = factoryConfiguration.getSession()) {
            String userJobRole = session.createQuery(
                            "SELECT u.role FROM User u WHERE u.username = :userName AND u.password = :password", String.class)
                    .setParameter("userName", userName)
                    .setParameter("password", password)
                    .uniqueResult();
            return Optional.ofNullable(userJobRole);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
