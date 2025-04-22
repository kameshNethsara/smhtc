package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.PatientBO;
import lk.ijse.gdse.smhtc.dao.custom.PatientDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.PatientDAOImpl;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.entity.Patient;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PatientBOImpl implements PatientBO {

    PatientDAO patientDAO = new PatientDAOImpl();

    @Override
    public String getNextPatientId() {
        return patientDAO.getNextId();
    }

    @Override
    public boolean savePatient(PatientDTO dto) {
        Patient entity = new Patient(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMedicalHistory(),
                null // payments = null for now
        );
        return patientDAO.save(entity);
    }

    @Override
    public boolean updatePatient(PatientDTO dto) {
        Patient entity = new Patient(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAddress(),
                dto.getMedicalHistory(),
                null
        );
        return patientDAO.update(entity);
    }

    @Override
    public boolean deletePatient(String pk) {
        return patientDAO.delete(pk);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientDAO.getAll().stream()
                .map(entity -> new PatientDTO(
                        entity.getPatientId(),
                        entity.getName(),
                        entity.getEmail(),
                        entity.getPhone(),
                        entity.getAddress(),
                        entity.getMedicalHistory()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PatientDTO> findByPatientId(String pk) {
        return patientDAO.findById(pk).map(entity -> new PatientDTO(
                entity.getPatientId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                entity.getMedicalHistory()
        ));
    }

    @Override
    public List<Patient> findByName(String name) {
        return patientDAO.findByName(name);
    }
}
