package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.TherapistProgramBO;
import lk.ijse.gdse.smhtc.dao.DAOFactory;
import lk.ijse.gdse.smhtc.dao.custom.TherapistDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapistProgramDAO;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.dto.TherapistProgramDTO;
import lk.ijse.gdse.smhtc.entity.Therapist;
import lk.ijse.gdse.smhtc.entity.TherapistProgram;
import lk.ijse.gdse.smhtc.entity.TherapistProgramId;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapistProgramBOImpl implements TherapistProgramBO {
    TherapistProgramDAO therapistProgramDAO = (TherapistProgramDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST_PROGRAM);
    TherapistDAO therapistDAO = (TherapistDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPIST);
    TherapyProgrammeDAO therapyProgramDAO = (TherapyProgrammeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);

    @Override
    public boolean saveTherapistProgram(String therapistId, String programId) {
        Optional<Therapist> therapistOtp = therapistDAO.findById(therapistId);
        Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(programId);

        if (therapistOtp.isEmpty() || programOpt.isEmpty()) {
            return false;
        }
        if (therapistProgramDAO.findById(therapistId, programId).isPresent()) {
            return false;
        }
        Therapist therapist = therapistOtp.get();
        TherapyProgram program = programOpt.get();

        TherapistProgram therapistProgram = new TherapistProgram();
        therapistProgram.setId(new TherapistProgramId(therapistId, programId));
        therapistProgram.setTherapist(therapist);
        therapistProgram.setTherapyProgram(program);

        return therapistProgramDAO.save(therapistProgram);
    }

    @Override
    public boolean updateTherapistProgram(String therapistId, String programId) {
        Optional<Therapist> therapistOpt = therapistDAO.findById(therapistId);
        Optional<TherapyProgram> programOpt = therapyProgramDAO.findById(programId);

        if (therapistOpt.isEmpty() || programOpt.isEmpty()) {
            return false;
        }

        Therapist therapist = therapistOpt.get();
        TherapyProgram therapyProgram = programOpt.get();

        TherapistProgram entity = new TherapistProgram(
                new TherapistProgramId(therapistId, programId),
                therapist,
                therapyProgram
        );

        return therapistProgramDAO.update(entity);
    }

    @Override
    public boolean deleteTherapistProgram(String therapistId, String programId) {
        return therapistProgramDAO.delete(therapistId, programId);
    }

    @Override
    public TherapistProgramDTO findById(String therapistId, String programId) {
        Optional<TherapistProgram> result = therapistProgramDAO.findById(therapistId, programId);
        if (result.isPresent()) {
            TherapistProgram entity = result.get();
            return new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
        } else {
            return null;
        }
    }

    @Override
    public List<TherapistProgramDTO> getAllTherapistPrograms() {

        List<TherapistProgram> programs = therapistProgramDAO.getAll();
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;

    }

    @Override
    public List<TherapistProgramDTO> getTherapistProgramsByTherapist(String id) {

        List<TherapistProgram> programs = therapistProgramDAO.findByTherapist(id);
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;

    }

    @Override
    public List<TherapistProgramDTO> findByProgramName(String name) {
        List<TherapistProgram> programs = therapistProgramDAO.findByProgramName(name);
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<TherapistProgramDTO> getTherapistProgramsByTherapistId(String therapistId) {
        List<TherapistProgram> programs = therapistProgramDAO.findByTherapistId(therapistId);
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<TherapistProgramDTO> getTherapistProgramsByProgramId(String programId) {
        List<TherapistProgram> programs = therapistProgramDAO.findByProgramId(programId);
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;
    }

    @Override
    public List<TherapistProgramDTO> findIdList(String id) {
        List<TherapistProgram> programs = therapistProgramDAO.findIdList(id);
        List<TherapistProgramDTO> dtos = new ArrayList<>();

        for (TherapistProgram entity : programs) {
            TherapistProgramDTO dto = new TherapistProgramDTO(
                    entity.getId().getTherapistId(),
                    entity.getId().getProgramId(),
                    entity.getTherapyProgram().getName()
            );
            dtos.add(dto);
        }

        return dtos;
    }

}
