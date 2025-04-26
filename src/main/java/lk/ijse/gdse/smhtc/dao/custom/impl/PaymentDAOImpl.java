package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.PaymentDAO;
import lk.ijse.gdse.smhtc.entity.Patient;
import lk.ijse.gdse.smhtc.entity.Payment;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PaymentDAOImpl implements PaymentDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        Optional<String> lastPk = getLastPK();
        if (lastPk.isPresent()) {
            String id = lastPk.get();
            int num = Integer.parseInt(id.replace("PAY-", "")) + 1;
            return String.format("PAY-%03d", num);
        } else {
            return "PAY-001";
        }
    }

    @Override
    public boolean save(Payment entity) {
        Session session = factoryConfiguration.getSession();
        try {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Payment entity) {
        Session session = factoryConfiguration.getSession();
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String pk) {
        Session session = factoryConfiguration.getSession();
        try {
            session.beginTransaction();
            Payment payment = session.get(Payment.class, pk);
            if (payment != null) {
                session.delete(payment);
                session.getTransaction().commit();
                return true;
            } else {
                session.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "SELECT p FROM Payment p " +
                    "JOIN FETCH p.patient " +
                    "JOIN FETCH p.therapyProgram " +
                    "LEFT JOIN FETCH p.therapySession"; // LEFT JOIN for nullable session
            return session.createQuery(hql, Payment.class).list();
        } finally {
            session.close(); // Close session after processing
        }
    }

    @Override
    public Optional<Payment> findById(String pk) {
        Session session = factoryConfiguration.getSession();
        try {
            Payment payment = session.get(Payment.class, pk);
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Payment> findByName(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        try {
            String hql = "SELECT p.paymentId FROM Payment p ORDER BY p.paymentId DESC";
            String lastId = session.createQuery(hql, String.class)
                    .setMaxResults(1)
                    .uniqueResult();
            return Optional.ofNullable(lastId);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
