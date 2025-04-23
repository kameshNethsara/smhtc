package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.dao.custom.PaymentDAO;
import lk.ijse.gdse.smhtc.entity.Payment;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PaymentDAOImpl implements PaymentDAO {

    private final Session session = factoryConfiguration.getSession();

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
        try {
            return session.createQuery("FROM Payment", Payment.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // empty immutable list
        }
    }

    @Override
    public Optional<Payment> findById(String pk) {
        try {
            Payment payment = session.get(Payment.class, pk);
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<String> getLastPK() {
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
