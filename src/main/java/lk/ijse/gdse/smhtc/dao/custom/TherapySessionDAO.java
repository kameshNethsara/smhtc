package lk.ijse.gdse.smhtc.dao.custom;
import lk.ijse.gdse.smhtc.dao.CrudDAO;
import lk.ijse.gdse.smhtc.entity.TherapySession;

import java.util.List;
import java.util.Optional;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {

//    public boolean save(TherapySession entity);
//    public boolean update(TherapySession entity);
//    public boolean delete(String pk);
//    public List<TherapySession> getAll();
//    public Optional<String> getLastPK();
//    Optional<TherapySession> findById(String sessionId);
    List<TherapySession> findByPatientId(String patientId);


}
