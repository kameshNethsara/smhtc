package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientBO extends SuperBO {
    public String getNextPatientId();
    public boolean savePatient(PatientDTO patientDTO);
    public boolean updatePatient(PatientDTO patientDTO);
    public boolean deletePatient(String pk);
    public List<PatientDTO> getAllPatients();
    public Optional<PatientDTO> findByPatientId(String pk);
    ///////////////////////////////////////////////////////
    public List<PatientDTO> findByName(String name);
    public List<PatientDTO> findByPhone(String phone);
}
