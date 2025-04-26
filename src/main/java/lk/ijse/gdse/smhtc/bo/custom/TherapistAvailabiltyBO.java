package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse.smhtc.entity.TherapistAvailability;

import java.time.LocalDate;
import java.util.List;

public interface TherapistAvailabiltyBO extends SuperBO {
    public String getNextId();
    public List<TherapistAvailabilityDTO> getAll();
    public boolean save(TherapistAvailabilityDTO dto);
    public boolean update(TherapistAvailabilityDTO dto);
    public boolean delete(String availabilityId);
    public List<TherapistAvailabilityDTO> findByTherapistAndDate(String therapistId, LocalDate date);
    public List<TherapistAvailabilityDTO> findByTherapistId(String therapistId);
    public List<TherapistAvailabilityDTO> findByDate(LocalDate date);

}
