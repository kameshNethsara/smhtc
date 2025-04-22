package lk.ijse.gdse.smhtc.dao.custom;
import lk.ijse.gdse.smhtc.entity.Therapist;

import java.util.List;
import java.util.Optional;

public interface TherapistDAO {
    public String getNextId();
    public boolean save(Therapist entity);
    public boolean update(Therapist entity);
    public boolean delete(String pk);
    public List<Therapist> getAll();
    public Optional<Therapist> findById(String pk);
    public Optional<String> getLastPK();
    ///////////////////////////////////
    public List<Therapist> findByPhone(String phone);
    public List<Therapist> findByName(String name);
}
