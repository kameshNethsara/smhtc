package lk.ijse.gdse.smhtc.dao.custom.impl;


import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.smhtc.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        Optional<String> lastPkOpt = getLastPK();

        if (lastPkOpt.isPresent()) {
            String lastPk = lastPkOpt.get();
            String numericPart = lastPk.substring(2);
            int currentId = Integer.parseInt(numericPart);
            int nextId = currentId + 1;
            return String.format("TS%03d", nextId);
        }

        return "TS001";
    }

    @Override
    public boolean save(TherapySession entity) {
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
    public boolean update(TherapySession entity) {
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
    public boolean delete(String sessionId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapySession therapySession = session.get(TherapySession.class, sessionId);
            if (therapySession != null) {
                session.remove(therapySession);
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
    public List<TherapySession> getAll() {
        Session session = factoryConfiguration.getSession();
        try {
            // Eagerly fetch all associations in one query
            String hql = "SELECT ts FROM TherapySession ts " +
                    "JOIN FETCH ts.therapist " +
                    "JOIN FETCH ts.patient " +
                    "JOIN FETCH ts.therapyProgram";
            return session.createQuery(hql, TherapySession.class).list();
        } finally {
            session.close(); // Close session after processing
        }
    }

    @Override
    public Optional<TherapySession> findByName(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<TherapySession> findById(String sessionId) {
        Session session = factoryConfiguration.getSession();
        TherapySession sessionEntity = null;
        try {
            sessionEntity = session.get(TherapySession.class, sessionId);
        } finally {
            session.close();
        }
        return Optional.ofNullable(sessionEntity);
    }

    @Override
    public List<TherapySession> findByPatientId(String patientId) {
        Session session = factoryConfiguration.getSession();
        List<TherapySession> sessions = session.createQuery(
                        "FROM TherapySession ts WHERE ts.patient.patientId = :id", TherapySession.class)
                .setParameter("id", patientId)
                .list();
        session.close();
        return sessions;
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery("SELECT s.sessionId FROM TherapySession s ORDER BY s.sessionId DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return Optional.ofNullable(lastPk);
    }

}
