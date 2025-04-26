package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse.smhtc.entity.TherapistAvailability;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TherapistAvailabilityDAOImpl implements TherapistAvailabilityDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    // TherapistAvailabilityDAOImpl.java
    @Override
    public List<TherapistAvailability> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            // Fetch all associations in one query
            String hql = "SELECT ta FROM TherapistAvailability ta " +
                    "JOIN FETCH ta.therapist " +
                    "LEFT JOIN FETCH ta.sessionId"; // Adjust if session is nullable
            return session.createQuery(hql, TherapistAvailability.class).list();
        } finally {
            session.close(); // Close session after processing
        }
    }

    @Override
    public Optional<TherapistAvailability> findById(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<TherapistAvailability> findByName(String pk) {
        return Optional.empty();
    }


    @Override
    public String getNextId() {
        Optional<String> lastPkOptional = getLastPK();

        if (lastPkOptional.isPresent()) {
            String lastPk = lastPkOptional.get();
            int numberPart = Integer.parseInt(lastPk.substring(1));
            int nextNumber = numberPart + 1;
            return String.format("TA%03d", nextNumber);
        }

        return "TA001"; // Default if no record exists
    }

    @Override
    public boolean save(TherapistAvailability entity) {
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
    public boolean update(TherapistAvailability entity) {
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
    public boolean delete(String availabilityId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapistAvailability availability = session.get(TherapistAvailability.class, availabilityId);
            if (availability != null) {
                session.remove(availability);
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
    public List<TherapistAvailability> findByTherapistAndDate(String therapistId, LocalDate date) {
        Session session = FactoryConfiguration.getInstance().getSession();
        List<TherapistAvailability> availabilities = session.createQuery(
                        "FROM TherapistAvailability ta WHERE ta.therapist.therapistId = :id AND ta.availableDate = :date",
                        TherapistAvailability.class)
                .setParameter("id", therapistId)
                .setParameter("date", date)
                .getResultList();
        session.close();
        return availabilities;
    }

    @Override
    public List<TherapistAvailability> findByTherapistId(String therapistId) {
        Session session = factoryConfiguration.getSession();
        List<TherapistAvailability> list = session.createQuery(
                        "FROM TherapistAvailability ta WHERE ta.therapist.therapistId = :id",
                        TherapistAvailability.class)
                .setParameter("id", therapistId)
                .list();
        session.close();
        return list;
    }

    @Override
    public List<TherapistAvailability> findByDate(LocalDate date) {
        Session session = factoryConfiguration.getSession();
        List<TherapistAvailability> list = session.createQuery(
                        "FROM TherapistAvailability ta WHERE ta.availableDate = :date",
                        TherapistAvailability.class)
                .setParameter("date", date)
                .list();
        session.close();
        return list;
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery(
                        "SELECT ta.availabilityId FROM TherapistAvailability ta ORDER BY ta.availabilityId DESC",
                        String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return Optional.ofNullable(lastPk);
    }



}
