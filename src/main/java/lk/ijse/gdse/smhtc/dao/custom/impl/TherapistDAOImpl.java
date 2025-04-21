package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.TherapistDAO;
import lk.ijse.gdse.smhtc.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TherapistDAOImpl implements TherapistDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        Optional<String> lastPkOptional = getLastPK();

        if (lastPkOptional.isPresent()) {
            String lastPk = lastPkOptional.get(); // Example: "T005"
            int numberPart = Integer.parseInt(lastPk.substring(1));
            int nextNumber = numberPart + 1;
            return String.format("T%03d", nextNumber); // T006
        }

        return "T001"; // Default if no record exists
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery("SELECT t.therapistId FROM Therapist t ORDER BY t.therapistId DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();

        return Optional.ofNullable(lastPk);
    }

    @Override
    public boolean save(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Therapist entity) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(String pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.find(Therapist.class, pk);
            if (therapist != null) {
                session.remove(therapist);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Therapist> getAll() {
        Session session = factoryConfiguration.getSession();
        List<Therapist> therapistList = session.createQuery("FROM Therapist", Therapist.class).list();
        session.close();
        return therapistList;
    }

    @Override
    public Optional<Therapist> findById(String pk) {
        Session session = factoryConfiguration.getSession();
        Therapist therapist = session.get(Therapist.class, pk);
        session.close();
        return Optional.ofNullable(therapist);
    }
}
