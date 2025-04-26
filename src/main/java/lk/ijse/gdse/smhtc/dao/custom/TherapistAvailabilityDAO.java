package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.dao.CrudDAO;
import lk.ijse.gdse.smhtc.entity.TherapistAvailability;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TherapistAvailabilityDAO extends CrudDAO<TherapistAvailability> {
//    public String getNextId() {
//    public List<TherapistAvailability> getAll();
//    public boolean save(TherapistAvailability entity);
//    public boolean update(TherapistAvailability entity);
//    public boolean delete(String availabilityId);
    public List<TherapistAvailability> findByTherapistAndDate(String therapistId, LocalDate date);
    public List<TherapistAvailability> findByTherapistId(String therapistId);
    public List<TherapistAvailability> findByDate(LocalDate date);
//    public Optional<String> getLastPK();
}
