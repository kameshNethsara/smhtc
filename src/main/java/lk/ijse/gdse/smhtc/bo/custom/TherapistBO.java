package lk.ijse.gdse.smhtc.bo.custom;



import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;

import java.util.List;
import java.util.Optional;

public interface TherapistBO extends SuperBO {
    public String getNextTherapistId();
    public boolean saveTherapist(TherapistDTO therapistDTO);
    public boolean updateTherapist(TherapistDTO therapistDTO);
    public boolean deleteTherapist(String pk);
    public List<TherapistDTO> getAllTherapists();
    public Optional<TherapistDTO> findByTherapistId(String pk);
    ////////////////////////////////////////////////////////////
    public List<TherapistDTO> findByPhone(String phone);
    public List<TherapistDTO> findByName(String name);
}
