package lk.ijse.gdse.smhtc.dao.custom;

import lk.ijse.gdse.smhtc.dao.CrudDAO;
import lk.ijse.gdse.smhtc.entity.Patient;
import lk.ijse.gdse.smhtc.entity.Therapist;

import java.util.List;
import java.util.Optional;

public interface PatientDAO extends CrudDAO<Patient> {
//    public String getNextId();
//    public boolean save(Patient entity);
//    public boolean update(Patient entity);
//    public boolean delete(String pk);
//    public List<Patient> getAll();
//    public Optional<Patient> findById(String pk);
//    public Optional<String> getLastPK();
    /////////////////////////////////////////
    public List<Patient> findByNameList(String name);
    public List<Patient> findByPhone(String phone);
}
