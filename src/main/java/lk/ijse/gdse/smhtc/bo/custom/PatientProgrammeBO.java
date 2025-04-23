package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PatientProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;

import java.util.List;
import java.util.Optional;

public interface PatientProgrammeBO {
    public String getNextPatientId();
    public String getNextProgramId();
    public boolean savePatientProgramme(PatientProgrammeDTO patientProgrammeDTO);
    public boolean updatePatientProgramme(PatientProgrammeDTO patientProgrammeDTO);
    public boolean deletePatientProgramme(String pk);
    public List<PatientProgrammeDTO> getAllPatientProgrammes();
    //public Optional<PatientProgrammeDTO> findByPatientProgrammeId(String pk);
    public List<PatientDTO> findByPatientName(String name);
    public List<PatientDTO> findByPatientPhone(String phone);
    public List<TherapyProgrammeDTO> findByTherapyProgrammeName(String programmeName);
}
