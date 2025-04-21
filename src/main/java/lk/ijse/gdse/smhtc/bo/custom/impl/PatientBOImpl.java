package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.PatientBO;
import lk.ijse.gdse.smhtc.dto.PatientDTO;

import java.util.List;
import java.util.Optional;

public class PatientBOImpl implements PatientBO {
    @Override
    public String getNextPatientId() {
        return "";
    }

    @Override
    public boolean savePatient(PatientDTO patientDTO) {
        return false;
    }

    @Override
    public boolean updatePatient(PatientDTO patientDTO) {
        return false;
    }

    @Override
    public boolean deletePatient(String pk) {
        return false;
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        return List.of();
    }

    @Override
    public Optional<PatientDTO> findByPatientId(String pk) {
        return Optional.empty();
    }
}
