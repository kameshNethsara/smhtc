package lk.ijse.gdse.smhtc.dao;

import lk.ijse.gdse.smhtc.entity.User;

import java.util.List;
import java.util.Optional;

public interface CrudDAO <T> extends SuperDAO{
    String getNextId();
    boolean save(T entity);
    boolean update(T entity);
    boolean delete(String pk);
    List<T> getAll();
    Optional<T> findById(String pk);
    Optional<T> findByName(String pk);
    Optional<String> getLastPK();

}