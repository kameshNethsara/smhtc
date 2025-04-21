package lk.ijse.gdse.smhtc.dao.custom.impl;

import lk.ijse.gdse.smhtc.dao.custom.TherapistDAO;
import lk.ijse.gdse.smhtc.entity.Therapist;

import java.util.List;
import java.util.Optional;

public class TherapistDAOImpl implements TherapistDAO {
    @Override
    public String getNextId() {
        return "";
    }

    @Override
    public boolean save(Therapist entity) {
        return false;
    }

    @Override
    public boolean update(Therapist entity) {
        return false;
    }

    @Override
    public boolean delete(String pk) {
        return false;
    }

    @Override
    public List<Therapist> getAll() {
        return List.of();
    }

    @Override
    public Optional<Therapist> findById(String pk) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getLastPK() {
        return Optional.empty();
    }
}
