package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.entity.Patient;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.List;
import java.util.Optional;

public interface TherapyProgrammeDAO {
    public String getNextId();
    public boolean save(TherapyProgram entity);
    public boolean update(TherapyProgram entity);
    public boolean delete(String pk);
    public List<TherapyProgram> getAll();
    public Optional<TherapyProgram> findById(String pk);
    public Optional<String> getLastPK();
    /////////////////////////////////////////
    public List<TherapyProgram> findByName(String name);
}
