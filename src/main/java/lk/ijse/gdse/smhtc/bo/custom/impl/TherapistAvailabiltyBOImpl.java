package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.TherapistAvailabiltyBO;
import lk.ijse.gdse.smhtc.dao.DAOFactory;
import lk.ijse.gdse.smhtc.dao.custom.TherapistAvailabilityDAO;
import lk.ijse.gdse.smhtc.dto.TherapistAvailabilityDTO;
import lk.ijse.gdse.smhtc.entity.Therapist;
import lk.ijse.gdse.smhtc.entity.TherapySession;
import lk.ijse.gdse.smhtc.entity.TherapistAvailability;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TherapistAvailabiltyBOImpl implements TherapistAvailabiltyBO {

    private final TherapistAvailabilityDAO availabilityDAO =
            (TherapistAvailabilityDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST_AVAILABILITY);

    @Override
    public String getNextId() {
        return availabilityDAO.getNextId();
    }

    @Override
    public List<TherapistAvailabilityDTO> getAll() {
        return availabilityDAO.getAll().stream()
                .map(entity -> new TherapistAvailabilityDTO(
                        entity.getAvailabilityId(),
                        entity.getTherapist().getTherapistId(),
                        entity.getSessionId(),
                        entity.getAvailability(),
                        entity.getAvailableDate(),
                        entity.getAvailableTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(TherapistAvailabilityDTO dto) {
        Therapist therapist = new Therapist();
        therapist.setTherapistId(dto.getTherapistId()); // Only set the therapist ID

        TherapistAvailability entity = new TherapistAvailability(
                dto.getAvailabilityId(),
                therapist,
                dto.getSessionId(), // Directly assign the sessionId String
                dto.getAvailability(),
                dto.getAvailableDate(),
                dto.getAvailableTime()
        );
        return availabilityDAO.save(entity);
    }

    @Override
    public boolean update(TherapistAvailabilityDTO dto) {
        Therapist therapist = new Therapist();
        therapist.setTherapistId(dto.getTherapistId()); // Only set the therapist ID

        TherapistAvailability entity = new TherapistAvailability(
                dto.getAvailabilityId(),
                therapist,
                dto.getSessionId(), // Directly assign the sessionId String
                dto.getAvailability(),
                dto.getAvailableDate(),
                dto.getAvailableTime()
        );
        return availabilityDAO.update(entity);
    }

    @Override
    public boolean delete(String availabilityId) {
        return availabilityDAO.delete(availabilityId);
    }

    @Override
    public List<TherapistAvailabilityDTO> findByTherapistAndDate(String therapistId, LocalDate date) {
        return availabilityDAO.findByTherapistAndDate(therapistId, date).stream()
                .map(entity -> new TherapistAvailabilityDTO(
                        entity.getAvailabilityId(),
                        entity.getTherapist().getTherapistId(),
                        entity.getSessionId(),
                        entity.getAvailability(),
                        entity.getAvailableDate(),
                        entity.getAvailableTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TherapistAvailabilityDTO> findByTherapistId(String therapistId) {
        return availabilityDAO.findByTherapistId(therapistId).stream()
                .map(entity -> new TherapistAvailabilityDTO(
                        entity.getAvailabilityId(),
                        entity.getTherapist().getTherapistId(),
                        entity.getSessionId(),
                        entity.getAvailability(),
                        entity.getAvailableDate(),
                        entity.getAvailableTime()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TherapistAvailabilityDTO> findByDate(LocalDate date) {
        return availabilityDAO.findByDate(date).stream()
                .map(entity -> new TherapistAvailabilityDTO(
                        entity.getAvailabilityId(),
                        entity.getTherapist().getTherapistId(),
                        entity.getSessionId(),
                        entity.getAvailability(),
                        entity.getAvailableDate(),
                        entity.getAvailableTime()
                ))
                .collect(Collectors.toList());
    }
}
