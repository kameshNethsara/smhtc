package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.custom.PatientDAO;
import lk.ijse.gdse.smhtc.entity.Patient;
import lk.ijse.gdse.smhtc.entity.Therapist;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PatientDAOImpl implements PatientDAO {

    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getNextId() {
        Optional<String> lastPkOptional = getLastPK();

        if (lastPkOptional.isPresent()) {
            String lastPk = lastPkOptional.get(); // e.g., "P005"
            int numberPart = Integer.parseInt(lastPk.substring(1));
            int nextNumber = numberPart + 1;
            return String.format("P%03d", nextNumber); // P006
        }

        return "P001"; // Default if no record exists
    }

    @Override
    public boolean save(Patient entity) {
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
    public boolean update(Patient entity) {
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
            Patient patient = session.find(Patient.class, pk);
            if (patient != null) {
                session.remove(patient);
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
    public List<Patient> getAll() {
        Session session = factoryConfiguration.getSession();
        List<Patient> patientList = session.createQuery("FROM Patient", Patient.class).list();
        session.close();
        return patientList;
    }

    @Override
    public Optional<Patient> findById(String pk) {
        Session session = factoryConfiguration.getSession();
        Patient patient = session.get(Patient.class, pk);
        session.close();
        return Optional.ofNullable(patient);
    }

    @Override
    public Optional<String> getLastPK() {
        Session session = factoryConfiguration.getSession();
        String lastPk = session.createQuery("SELECT p.patientId FROM Patient p ORDER BY p.patientId DESC", String.class)
                .setMaxResults(1)
                .uniqueResult();
        session.close();

        return Optional.ofNullable(lastPk);
    }

    @Override
    public List<Patient> findByName(String name) {
        Session session = factoryConfiguration.getSession();
        List<Patient> patientList = session.createQuery("FROM Patient WHERE name LIKE :name", Patient.class)
                .setParameter("name", "%" + name + "%")
                .list();
        session.close();
        return patientList;
    }

    @Override
    public List<Patient> findByPhone(String phone) {
        Session session = factoryConfiguration.getSession();
        List<Patient> patientList = session.createQuery("FROM Patient WHERE phone LIKE :phone", Patient.class)
                .setParameter("phone", "%" + phone + "%")
                .list();
        session.close();
        return patientList;
    }


}
