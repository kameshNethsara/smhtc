package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.dao.custom.PatientProgramDAO;
import lk.ijse.gdse.smhtc.entity.PatientProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PatientProgramDAOImpl implements PatientProgramDAO {
    private final Session session = factoryConfiguration.getSession();

    @Override
    public boolean save(PatientProgram entity) {
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
    public boolean update(PatientProgram entity) {
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
        List<PatientProgram> patientProgramList = session.createQuery("FROM PatientProgram", PatientProgram.class).list();
        session.close();
        return patientProgramList;
    }

    @Override
    public Optional<PatientProgram> findById(String pk) {
        PatientProgram patientProgram = session.get(PatientProgram.class, pk);
        session.close();
        return Optional.ofNullable(patientProgram);
    }
}
