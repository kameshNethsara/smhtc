package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.entity.PatientProgram;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface PatientProgramDAO {
    public boolean save(PatientProgram entity);
    public boolean update(PatientProgram entity);
    public boolean delete(String pk);
    public List<PatientProgram> getAll();
    public Optional<PatientProgram> findById(String pk);
}
