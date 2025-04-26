package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.PatientProgrammeBO;
import lk.ijse.gdse.smhtc.dao.custom.PatientDAO;
import lk.ijse.gdse.smhtc.dao.custom.PatientProgramDAO;
import lk.ijse.gdse.smhtc.dao.custom.PaymentDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.smhtc.dao.custom.impl.PatientProgramDAOImpl;
import lk.ijse.gdse.smhtc.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.gdse.smhtc.dao.custom.impl.TherapyProgrammeDAOImpl;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PatientProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse.smhtc.config.FactoryConfiguration.factoryConfiguration;

public class PatientProgrammeBOImpl implements PatientProgrammeBO {

    PatientDAO patientDAO = new PatientDAOImpl();
    TherapyProgrammeDAO therapyProgrammeDAO = new TherapyProgrammeDAOImpl();
    PatientProgramDAO patientProgrammeDAO = new PatientProgramDAOImpl();
    PaymentDAO paymentDAO = new PaymentDAOImpl();

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
        return paymentDAO.getNextId();
    }

    @Override
    public boolean savePatientProgramme(PatientProgrammeDTO dto) {
        // Retrieve Patient, TherapyProgram, and Payment from the database
        Optional<Patient> patientOptional = patientDAO.findById(dto.getPatientId());
        Optional<TherapyProgram> therapyProgramOptional = therapyProgrammeDAO.findById(dto.getProgramId());
        Optional<Payment> paymentOptional = paymentDAO.findById(dto.getPaymentId());

        // Check if any of the required entities are not found
        if (patientOptional.isEmpty()) {
            System.err.println("Patient with ID " + dto.getPatientId() + " not found.");
            return false;
        }
        if (therapyProgramOptional.isEmpty()) {
            System.err.println("Therapy Program with ID " + dto.getProgramId() + " not found.");
            return false;
        }
//        if (paymentOptional.isEmpty()) {
//            System.err.println("Payment with ID " + dto.getPaymentId() + " not found.");
//            return false;
//        }

        // Create a new PatientProgram object
        PatientProgram patientProgram = new PatientProgram();
        PatientProgramId id = new PatientProgramId(dto.getPatientId(), dto.getProgramId());
        patientProgram.setId(id);
        patientProgram.setPatient(patientOptional.get());
        patientProgram.setTherapyProgram(therapyProgramOptional.get());
        //patientProgram.setPayment(paymentOptional.get());
        patientProgram.setPayment(paymentOptional.orElse(null));

        if (dto.getBalance() == null) {
            patientProgram.setBalance(therapyProgrammeDAO.findById(dto.getProgramId()).get().getFee());
        }else {
            patientProgram.setBalance(dto.getBalance());
        }

        // Parse the registration date and set it in the patientProgram object
        try {
            patientProgram.setRegistrationDate(LocalDate.parse(dto.getRegistrationDate()));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid registration date format: " + dto.getRegistrationDate());
            e.printStackTrace();
            return false;
        }

        // Save the patientProgram to the database
        try {
            boolean isSaved = patientProgrammeDAO.save(patientProgram);
            if (isSaved) {
                System.out.println("Patient program saved successfully.");
            } else {
                System.err.println("Failed to save patient program.");
            }
            return isSaved;
        } catch (Exception e) {
            System.err.println("An error occurred while saving the patient program.");
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean updatePatientProgramme(PatientProgrammeDTO dto) {
        // Retrieve Patient, TherapyProgram, and Payment from the database
        Optional<Patient> patientOptional = patientDAO.findById(dto.getPatientId());
        Optional<TherapyProgram> therapyProgramOptional = therapyProgrammeDAO.findById(dto.getProgramId());
        Optional<Payment> paymentOptional = paymentDAO.findById(dto.getPaymentId());

        // Check if any of the required entities are not found
        if (patientOptional.isEmpty()) {
            System.err.println("Patient with ID " + dto.getPatientId() + " not found.");
            return false;
        }
        if (therapyProgramOptional.isEmpty()) {
            System.err.println("Therapy Program with ID " + dto.getProgramId() + " not found.");
            return false;
        }
//        if (paymentOptional.isEmpty()) {
//            System.err.println("Payment with ID " + dto.getPaymentId() + " not found.");
//            return false;
//        }

        // Create a new PatientProgram object
        PatientProgram patientProgram = new PatientProgram();
        PatientProgramId id = new PatientProgramId(dto.getPatientId(), dto.getProgramId());
        patientProgram.setId(id);
        patientProgram.setPatient(patientOptional.get());
        patientProgram.setTherapyProgram(therapyProgramOptional.get());
        //patientProgram.setPayment(paymentOptional.get());
        patientProgram.setPayment(paymentOptional.orElse(null));

        if (dto.getBalance() == null) {
            patientProgram.setBalance(therapyProgrammeDAO.findById(dto.getProgramId()).get().getFee());
        }else {
            patientProgram.setBalance(dto.getBalance());
        }

        // Parse the registration date and set it in the patientProgram object
        try {
            patientProgram.setRegistrationDate(LocalDate.parse(dto.getRegistrationDate()));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid registration date format: " + dto.getRegistrationDate());
            e.printStackTrace();
            return false;
        }

        // Save the patientProgram to the database
        try {
            boolean isUpdated = patientProgrammeDAO.update(patientProgram);
            if (isUpdated) {
                System.out.println("Patient program saved successfully.");
            } else {
                System.err.println("Failed to save patient program.");
            }
            return isUpdated;
        } catch (Exception e) {
            System.err.println("An error occurred while saving the patient program.");
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean deletePatientProgramme(PatientProgramId pk) {
        return patientProgrammeDAO.delete(pk);
    }

    @Override
    public List<PatientProgrammeDTO> getAllPatientProgrammes() {
        List<PatientProgram> patientProgramList = patientProgrammeDAO.getAll();
        List<PatientProgrammeDTO> dtoList = new ArrayList<>();

        String PayID = "";
        for (PatientProgram pp : patientProgramList) {
            if (pp.getPayment() == null || pp.getPayment().getPaymentId() == null) {
                PayID = "N/A";
            } else {
                PayID = pp.getPayment().getPaymentId();
            }

            dtoList.add(
                    new PatientProgrammeDTO(
                            pp.getPatient().getPatientId(),
                            pp.getTherapyProgram().getProgramId(),
                            PayID,
                            therapyProgrammeDAO.findById(pp.getTherapyProgram().getProgramId()).get().getFee(),
                            pp.getBalance(),
                            pp.getRegistrationDate().toString()
                    )
            );
        }
        return dtoList;
    }

    @Override
    public List<PatientDTO> findByPatientName(String name) {
        List<Patient> patients = patientDAO.findByNameList(name);
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
        List<TherapyProgram> entities = therapyProgrammeDAO.findByNameList(programmeName);
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
