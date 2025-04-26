package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.PatientDTO;
import lk.ijse.gdse.smhtc.dto.PatientProgrammeDTO;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.PatientProgramId;

import java.util.List;
import java.util.Optional;

public interface PatientProgrammeBO extends SuperBO {
    public String getNextPatientId();
    public String getNextProgramId();
    public String getNextPaymentId();
    public boolean savePatientProgramme(PatientProgrammeDTO patientProgrammeDTO);
    public boolean updatePatientProgramme(PatientProgrammeDTO patientProgrammeDTO);
    public boolean deletePatientProgramme(PatientProgramId pk);
    public List<PatientProgrammeDTO> getAllPatientProgrammes();
    //public Optional<PatientProgrammeDTO> findByPatientProgrammeId(PatientProgramId pk);
    public List<PatientDTO> findByPatientName(String name);
    public List<PatientDTO> findByPatientPhone(String phone);
    public List<TherapyProgrammeDTO> findByTherapyProgrammeName(String programmeName);
}
