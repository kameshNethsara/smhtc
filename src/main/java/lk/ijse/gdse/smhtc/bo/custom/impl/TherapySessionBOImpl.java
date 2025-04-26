package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.BOFactory;
import lk.ijse.gdse.smhtc.bo.custom.TherapistAvailabiltyBO;
import lk.ijse.gdse.smhtc.bo.custom.TherapySessionBO;
import lk.ijse.gdse.smhtc.config.FactoryConfiguration;
import lk.ijse.gdse.smhtc.dao.DAOFactory;
import lk.ijse.gdse.smhtc.dao.custom.PatientDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapistDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapySessionDAO;
import lk.ijse.gdse.smhtc.dto.TherapySessionDTO;
import lk.ijse.gdse.smhtc.entity.Patient;
import lk.ijse.gdse.smhtc.entity.Therapist;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;
import lk.ijse.gdse.smhtc.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapySessionBOImpl implements TherapySessionBO {

    TherapySessionDAO therapySessionDAO = (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_SESSION);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    PatientDAO patientDAO = (PatientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PATIENT);
    TherapyProgrammeDAO therapyProgramDAO = (TherapyProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);

    TherapistAvailabiltyBO therapistAvailabiltyBO = (TherapistAvailabiltyBO) BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST_AVAILABILITY);


    @Override
    public boolean save(TherapySessionDTO dto) {
        boolean isCompleted = false;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Retrieve the entities from their respective DAOs
            Optional<Therapist> therapistOpt = therapistDAO.findById(dto.getTherapistId());
            Optional<Patient> patientOpt = patientDAO.findById(dto.getPatientId());
            Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(dto.getTherapyProgramId());

//            Optional<TherapistAvailability> availabilityOpt = availabilityDAO.findById(dto.getAvailabilityId());

            // Check if any of the required entities are not found
            if (therapistOpt.isEmpty() || patientOpt.isEmpty() || programOpt.isEmpty()) {
                return false;
            }

            // Create the TherapySession entity
            TherapySession therapySession = new TherapySession();
            therapySession.setSessionId(dto.getSessionId());
            therapySession.setTherapist(therapistOpt.get());
            therapySession.setPatient(patientOpt.get());
            therapySession.setTherapyProgram(programOpt.get());
//            therapySession.setTherapistAvailability(null); // Set null because we can not get the availability object
            therapySession.setSessionDate(dto.getSessionDate());
            therapySession.setSessionTime(dto.getSessionTime());
//            therapySession.setDuration(dto.getDuration());
            therapySession.setStatus(dto.getStatus());


//            // Convert the duration (in minutes) to a Duration object
//            Duration sessionDuration = Duration.ofMinutes(dto.getDuration());
//
//            // Attempt to book the time slot
//            boolean success = therapistAvailabiltyBO.bookTimeSlot(
//                    dto.getTherapistId(),
//                    dto.getSessionDate(),
//                    dto.getSessionTime(),
//                    sessionDuration
//            );

//            if (success) {
//                if (therapySessionDAO.save(therapySession)) {
//                    isCompleted = true;
//                    transaction.commit();
//                }
//            } else {
//                isCompleted = false;
//            }
            if (therapySessionDAO.save(therapySession)) {
                isCompleted = true;
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return isCompleted;

    }

    @Override
    public boolean update(TherapySessionDTO dto) {

        // Fetch related entities
        Optional<Therapist> therapistOpt = therapistDAO.findById(dto.getTherapistId());
        Optional<Patient> patientOpt = patientDAO.findById(dto.getPatientId());
        Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(dto.getTherapyProgramId());
        Optional<TherapySession> optionalSession = therapySessionDAO.findById(dto.getSessionId());

        // Return false if any essential entity is missing
        if (therapistOpt.isEmpty() || patientOpt.isEmpty() || programOpt.isEmpty() || optionalSession.isEmpty()) {
            return false;
        }

        // Load existing session
        TherapySession therapySession = optionalSession.get();

        // Update entity fields
        therapySession.setTherapist(therapistOpt.get());
        therapySession.setPatient(patientOpt.get());
        therapySession.setTherapyProgram(programOpt.get());

//        if (dto.getAvailabilityId() != null) {
////            therapySession.setTherapistAvailability(new TherapistAvailability(dto.getAvailabilityId()));
//            therapySession.setTherapistAvailability(null);
//        } else {
//            therapySession.setTherapistAvailability(null); // Optional: reset if null
//        }

        therapySession.setSessionDate(dto.getSessionDate());
        therapySession.setSessionTime(dto.getSessionTime());
        //therapySession.setDuration(dto.getDuration());
        therapySession.setStatus(dto.getStatus());

        // Save the updated session
        return therapySessionDAO.update(therapySession);
    }

    @Override
    public boolean delete(String sessionId) {
        Optional<TherapySession> optionalSession = therapySessionDAO.findById(sessionId);

        if (optionalSession.isEmpty()) return false;
        TherapySession session = optionalSession.get();

//        boolean restored = therapistAvailabiltyBO.restoreTimeSlot(
//                session.getTherapist().getTherapistId(),
//                session.getSessionDate(),
//                session.getSessionTime(),
//                //Duration.ofMinutes(session.getDuration())
//        );
//
//        if (!restored) return false;
        return therapySessionDAO.delete(sessionId);
    }

    @Override
    public List<TherapySessionDTO> getAll() {
        List<TherapySession> sessions = therapySessionDAO.getAll();
        ArrayList<TherapySessionDTO> sessionDtos = new ArrayList<>();

        for (TherapySession session : sessions) {
            TherapySessionDTO dto = new TherapySessionDTO();
            dto.setSessionId(session.getSessionId());
            dto.setTherapistId(session.getTherapist().getTherapistId());
            dto.setPatientId(session.getPatient().getPatientId());
            dto.setTherapyProgramId(session.getTherapyProgram().getProgramId());
//            dto.setAvailabilityId(session.getTherapistAvailability() != null
//                    ? session.getTherapistAvailability().getAvailability_id() : null);
            dto.setSessionDate(session.getSessionDate());
            dto.setSessionTime(session.getSessionTime());
//            dto.setDuration(session.getDuration());
            dto.setStatus(session.getStatus());

            sessionDtos.add(dto);
        }

        return sessionDtos;
    }

    @Override
    public TherapySessionDTO findBySessionId(String sessionId) {
        Optional<TherapySession> optional = therapySessionDAO.findById(sessionId);
        if (optional.isEmpty()) return null;

        TherapySession session = optional.get();
        TherapySessionDTO dto = new TherapySessionDTO();
        dto.setSessionId(session.getSessionId());
        dto.setTherapistId(session.getTherapist().getTherapistId());
        dto.setPatientId(session.getPatient().getPatientId());
        dto.setTherapyProgramId(session.getTherapyProgram().getProgramId());
//        dto.setAvailabilityId(session.getTherapistAvailability() != null
//                ? session.getTherapistAvailability().getAvailability_id() : null);
        dto.setSessionDate(session.getSessionDate());
        dto.setSessionTime(session.getSessionTime());
//        dto.setDuration(session.getDuration());
        dto.setStatus(session.getStatus());

        return dto;
    }

    @Override
    public List<TherapySessionDTO> findByPatientId(String patientId) {
        List<TherapySession> sessions = therapySessionDAO.findByPatientId(patientId);
        ArrayList<TherapySessionDTO> sessionDtos = new ArrayList<>();

        for (TherapySession session : sessions) {
            TherapySessionDTO dto = new TherapySessionDTO();
            dto.setSessionId(session.getSessionId());
            dto.setTherapistId(session.getTherapist().getTherapistId());
            dto.setPatientId(session.getPatient().getPatientId());
            dto.setTherapyProgramId(session.getTherapyProgram().getProgramId());
//            dto.setAvailabilityId(session.getTherapistAvailability() != null
//                    ? session.getTherapistAvailability().getAvailability_id() : null);
            dto.setSessionDate(session.getSessionDate());
            dto.setSessionTime(session.getSessionTime());
//            dto.setDuration(session.getDuration());
            dto.setStatus(session.getStatus());

            sessionDtos.add(dto);
        }

        return sessionDtos;

    }

    @Override
    public String getNextSessionPK() {
        return "";
    }

}