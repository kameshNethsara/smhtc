package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.PatientProgrammeBO;
import lk.ijse.gdse.smhtc.dao.custom.PatientDAO;
import lk.ijse.gdse.smhtc.dao.custom.PatientProgramDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.smhtc.dao.custom.impl.PatientProgramDAOImpl;
import lk.ijse.gdse.smhtc.dao.custom.impl.TherapyProgrammeDAOImpl;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PatientProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PatientProgrammeBOImpl implements PatientProgrammeBO {

    PatientDAO patientDAO = new PatientDAOImpl();
    TherapyProgrammeDAO therapyProgrammeDAO = new TherapyProgrammeDAOImpl();
    PatientProgramDAO patientProgrammeDAO = new PatientProgramDAOImpl();
    //PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public String getNextPatientId() {
        return patientDAO.getNextId();
    }

    @Override
    public String getNextProgramId() {
        return therapyProgrammeDAO.getNextId();
    }

    @Override
    public String getNextPaymentId() {
        return "";
        //return paymentDAO.getNextId();
    }

    @Override
    public boolean savePatientProgramme(PatientProgrammeDTO dto) {
        try (Session session = factoryConfiguration.getSession()) {
            Transaction transaction = session.beginTransaction();

            // Composite Primary Key
            PatientProgramId id = new PatientProgramId(
                    dto.getPatientId(),
                    dto.getProgramId()
            );

            // Load related entities using session
            Patient patient = session.find(
                    Patient.class,
                    dto.getPatientId()
            );
            TherapyProgram therapyProgram = session.find(
                    TherapyProgram.class,
                    dto.getProgramId()
            );
            Payment payment = session.find(
                    Payment.class,
                    dto.getPaymentId()
            );

            // Parse date
            LocalDate regDate = LocalDate.parse(dto.getRegistrationDate());

            // Build entity
            PatientProgram entity = new PatientProgram(
                    id,
                    patient,
                    therapyProgram,
                    regDate,
                    payment);

            // Save through DAO (DAO does session.persist & transaction handling)
            boolean result = patientProgrammeDAO.save(entity);

            transaction.commit();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updatePatientProgramme(PatientProgrammeDTO dto) {
        try {
            // Step 1: Create Composite Primary Key
            PatientProgramId id = new PatientProgramId(dto.getPatientId(), dto.getProgramId());

            // Step 2: Load related entities using DAO or session
            Patient patient = patientDAO.findById(dto.getPatientId()).orElse(null);
            TherapyProgram therapyProgram = therapyProgrammeDAO.findById(dto.getProgramId()).orElse(null);
            Payment payment = new Payment();  // If PaymentDAO is available, load it; otherwise construct minimal

            payment.setId(dto.getPaymentId());

            if (patient == null || therapyProgram == null) {
                System.out.println("Related entities not found.");
                return false;
            }

            // Step 3: Parse date and construct entity
            LocalDate registrationDate = LocalDate.parse(dto.getRegistrationDate());

            PatientProgram patientProgram = new PatientProgram(id, patient, therapyProgram, registrationDate, payment);

            // Step 4: Update using DAO
            return patientProgrammeDAO.update(patientProgram);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deletePatientProgramme(PatientProgramId pk) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        try (session) {
            transaction = session.beginTransaction();
            // Step 1: Use the composite ID (PatientProgramId) to fetch the entity
            PatientProgram patientProgram = session.get(PatientProgram.class, pk);

            // Step 2: Check if the entity exists
            if (patientProgram != null) {
                // Step 3: Remove the entity from the database
                session.remove(patientProgram);
                transaction.commit();
                return true; // Successfully deleted
            } else {
                return false; // Entity not found
            }
        } catch (Exception e) {
            transaction.rollback(); // Rollback transaction in case of an error
            e.printStackTrace();
            return false; // Error during deletion
        }
        // Ensure session is closed
    }

    @Override
    public List<PatientProgrammeDTO> getAllPatientProgrammes() {
        // Step 1: Start a Hibernate session
        Session session = factoryConfiguration.getSession();
        try {
            // Step 2: Retrieve all PatientProgram entities from the database
            List<PatientProgram> patientPrograms = session.createQuery("FROM PatientProgram", PatientProgram.class).list();

            // Step 3: Convert PatientProgram entities to PatientProgrammeDTO objects
            List<PatientProgrammeDTO> patientProgrammeDTOs = new ArrayList<>();
            for (PatientProgram patientProgram : patientPrograms) {
                PatientProgrammeDTO dto = new PatientProgrammeDTO(
                        patientProgram.getId().getPatientId(),  // Get patientId from the embedded ID
                        patientProgram.getId().getProgramId(),  // Get programId from the embedded ID
                        patientProgram.getPayment().getId(),   // Assuming Payment entity has a getId() method
                        patientProgram.getRegistrationDate().toString() // Convert LocalDate to String
                );
                patientProgrammeDTOs.add(dto);
            }

            return patientProgrammeDTOs;  // Return the list of DTOs
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();  // Return an empty list in case of an error
        } finally {
            session.close();  // Always close the session
        }
    }

    @Override
    public List<PatientDTO> findByPatientName(String name) {
        List<Patient> patients = patientDAO.findByName(name);
        List<PatientDTO> patientDTOList = new ArrayList<>();

        for (Patient patient : patients) {
            patientDTOList.add(new PatientDTO(
                    patient.getPatientId(),
                    patient.getName(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getMedicalHistory()
            ));
        }
        return patientDTOList;
    }

    @Override
    public List<PatientDTO> findByPatientPhone(String phone) {
        List<Patient> patients = patientDAO.findByPhone(phone);
        List<PatientDTO> patientDTOList = new ArrayList<>();

        for (Patient patient : patients) {
            patientDTOList.add(new PatientDTO(
                    patient.getPatientId(),
                    patient.getName(),
                    patient.getPhone(),
                    patient.getEmail(),
                    patient.getAddress(),
                    patient.getMedicalHistory()
            ));
        }
        return patientDTOList;
    }

    @Override
    public List<TherapyProgrammeDTO> findByTherapyProgrammeName(String programmeName) {
        List<TherapyProgram> entities = therapyProgrammeDAO.findByName(programmeName);
        List<TherapyProgrammeDTO> dtoList = new ArrayList<>();
        for (TherapyProgram tp : entities) {
            dtoList.add(
                    new TherapyProgrammeDTO(
                            tp.getProgramId(),
                            tp.getName(),
                            tp.getDuration(),
                            tp.getFee()
                    )
            );
        }
        return dtoList;

    }
}
