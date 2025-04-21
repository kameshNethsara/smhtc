package lk.ijse.gdse.smhtc.bo.custom;



import lk.ijse.gdse.smhtc.dto.TherapistDTO;

import java.util.List;
import java.util.Optional;

public interface TherapistBO {
    public String getNextTherapistId();
    public boolean saveTherapist(TherapistDTO therapistDTO);
    public boolean updateTherapist(TherapistDTO therapistDTO);
    public boolean deleteTherapist(String pk);
    public List<TherapistDTO> getAllTherapists();
    public Optional<TherapistDTO> findByTherapistId(String pk);
}
