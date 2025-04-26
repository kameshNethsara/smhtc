package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.PatientProgramDAO;
import lk.ijse.gdse.smhtc.entity.PatientProgram;
import lk.ijse.gdse.smhtc.entity.PatientProgramId;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PatientProgramDAOImpl implements PatientProgramDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public boolean save(PatientProgram entity) {
//        Session session = factoryConfiguration.getSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            session.persist(entity);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            transaction.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            session.close();
//        }
        try (Session session = factoryConfiguration.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(PatientProgram entity) {
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
        return false;
    }

    @Override
    public boolean delete(PatientProgramId pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PatientProgram patientProgram = session.find(PatientProgram.class, pk);
            if (patientProgram != null) {
                session.remove(patientProgram);
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
    public List<PatientProgram> getAll() {
        Session session = factoryConfiguration.getSession();
//        List<PatientProgram> patientProgramList = session.createQuery("FROM PatientProgram", PatientProgram.class).list();
//        session.close();
//        return patientProgramList;

        //Session session = sessionFactory.openSession();
        Transaction transaction = null;

        List<PatientProgram> patientProgramList = null;
        try {
            transaction = session.beginTransaction();
            // Your query logic here
            patientProgramList = session.createQuery("FROM PatientProgram", PatientProgram.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return patientProgramList;
    }

    @Override
    public Optional<PatientProgram> findById(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<PatientProgram> findByName(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return Optional.empty();
    }

    @Override
    public Optional<PatientProgram> findById(PatientProgramId pk) {
        Session session = factoryConfiguration.getSession();
        PatientProgram patientProgram = session.get(PatientProgram.class, pk);
        session.close();
        return Optional.ofNullable(patientProgram);
    }
}
