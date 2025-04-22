package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.TherapistBO;
import lk.ijse.gdse.smhtc.dao.custom.TherapistDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.TherapistDAOImpl;
import lk.ijse.gdse.smhtc.dto.TherapistDTO;
import lk.ijse.gdse.smhtc.entity.Therapist;

import java.util.List;
import java.util.Optional;

public class TherapistBOImpl implements TherapistBO {
    TherapistDAO therapistDAO = new TherapistDAOImpl();
    @Override
    public String getNextTherapistId() {
        return therapistDAO.getNextId();
    }

    @Override
    public boolean saveTherapist(TherapistDTO therapistDTO) {
        Therapist therapist = new Therapist(
                therapistDTO.getId(),
                therapistDTO.getName(),
                therapistDTO.getEmail(),
                therapistDTO.getPhone(),
                therapistDTO.getAddress(),
                therapistDTO.getSpecialization()
        );
        return therapistDAO.save(therapist);
    }

    @Override
    public boolean updateTherapist(TherapistDTO therapistDTO) {
        Therapist therapist = new Therapist(
                therapistDTO.getId(),
                therapistDTO.getName(),
                therapistDTO.getEmail(),
                therapistDTO.getPhone(),
                therapistDTO.getAddress(),
                therapistDTO.getSpecialization()
        );
        return therapistDAO.update(therapist);
    }

    @Override
    public boolean deleteTherapist(String pk) {
        return therapistDAO.delete(pk);
    }

    @Override
    public List<TherapistDTO> getAllTherapists() {
        List<Therapist> therapistList = therapistDAO.getAll();
        return therapistList.stream()
               .map(therapist -> new TherapistDTO(
                       therapist.getTherapistId(),
                       therapist.getName(),
                       therapist.getEmail(),
                       therapist.getPhone(),
                       therapist.getAddress(),
                       therapist.getSpecialization()
               )).toList();
    }

    @Override
    public Optional<TherapistDTO> findByTherapistId(String pk) {
        Optional<Therapist> therapist = therapistDAO.findById(pk);
        return therapist.map(t -> new TherapistDTO(
                t.getTherapistId(),
                t.getName(),
                t.getEmail(),
                t.getPhone(),
                t.getAddress(),
                t.getSpecialization()
        ));
    }

    @Override
    public List<TherapistDTO> findByPhone(String phone) {
        return therapistDAO.findByPhone(phone).stream()
                .map(therapist -> new TherapistDTO(
                        therapist.getTherapistId(),
                        therapist.getName(),
                        therapist.getEmail(),
                        therapist.getPhone(),
                        therapist.getAddress(),
                        therapist.getSpecialization()
                )).toList();
    }

    @Override
    public List<TherapistDTO> findByName(String name) {
        return therapistDAO.findByName(name).stream()
                .map(therapist -> new TherapistDTO(
                        therapist.getTherapistId(),
                        therapist.getName(),
                        therapist.getEmail(),
                        therapist.getPhone(),
                        therapist.getAddress(),
                        therapist.getSpecialization()
                )).toList();
    }
}
