package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.dao.CrudDAO;
import lk.ijse.gdse.smhtc.entity.PatientProgram;
import lk.ijse.gdse.smhtc.entity.PatientProgramId;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public interface PatientProgramDAO extends CrudDAO<PatientProgram> {
//    public boolean save(PatientProgram entity);
//    public boolean update(PatientProgram entity);
    public boolean delete(PatientProgramId pk);
//    public List<PatientProgram> getAll();
    public Optional<PatientProgram> findById(PatientProgramId pk);
}
