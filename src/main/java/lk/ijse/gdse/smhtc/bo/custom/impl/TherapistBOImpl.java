package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.TherapistBO;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;

import java.util.List;
import java.util.Optional;

public class TherapistBOImpl implements TherapistBO {
    @Override
    public String getNextTherapistId() {
        return "";
    }

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        return false;
    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        return false;
    }

    @Override
    public boolean deleteTherapist(String pk) {
        return false;
    }

    @Override
    public List<TherapistDTO> getAllTherapists() {
        return List.of();
    }

    @Override
    public Optional<TherapistDTO> findByTherapistId(String pk) {
        return Optional.empty();
    }
}
