package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.TherapySessionDTO;

import java.util.List;

public interface TherapySessionBO extends SuperBO {
    boolean save(TherapySessionDTO dto);
    boolean update(TherapySessionDTO dto);
    boolean delete(String sessionId);
    List<TherapySessionDTO> getAll();
    TherapySessionDTO findBySessionId(String sessionId);
    List<TherapySessionDTO> findByPatientId(String patientId);
    String getNextSessionPK();

}
