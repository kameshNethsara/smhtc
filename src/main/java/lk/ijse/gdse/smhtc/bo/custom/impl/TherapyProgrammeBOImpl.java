package lk.ijse.gdse.smhtc.bo.custom.impl;

import lk.ijse.gdse.smhtc.bo.custom.TherapyProgrammeBO;
import lk.ijse.gdse.smhtc.dao.custom.TherapyProgrammeDAO;
import lk.ijse.gdse.smhtc.dao.custom.impl.TherapyProgrammeDAOImpl;
import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TherapyProgrammeBOImpl implements TherapyProgrammeBO {

    TherapyProgrammeDAO therapyProgrammeDAO = new TherapyProgrammeDAOImpl();

    @Override
    public String getNextTherapyProgrammeId() {
        return therapyProgrammeDAO.getNextId();
    }

    @Override
    public boolean saveTherapyProgramme(TherapyProgrammeDTO therapyProgrammeDTO) {
        TherapyProgram therapyProgram = new TherapyProgram(
                therapyProgrammeDTO.getId(),
                therapyProgrammeDTO.getName(),
                therapyProgrammeDTO.getDuration(),
                therapyProgrammeDTO.getFee());

        return therapyProgrammeDAO.save(therapyProgram);
    }

    @Override
    public boolean updateTherapyProgramme(TherapyProgrammeDTO therapyProgrammeDTO) {
        TherapyProgram entity = new TherapyProgram(
                therapyProgrammeDTO.getId(),
                therapyProgrammeDTO.getName(),
                therapyProgrammeDTO.getDuration(),
                therapyProgrammeDTO.getFee());
        return therapyProgrammeDAO.update(entity);
    }

    @Override
    public boolean deleteTherapyProgramme(String pk) {
        return therapyProgrammeDAO.delete(pk);
    }

    @Override
    public List<TherapyProgrammeDTO> getAllTherapyProgrammes() {
        List<TherapyProgram> entities = therapyProgrammeDAO.getAll();
        List<TherapyProgrammeDTO> dtoList = new ArrayList<>();
        for (TherapyProgram tp : entities) {
            dtoList.add(
                    new TherapyProgrammeDTO(
                            tp.getProgramId(),
                            tp.getName(),
                            tp.getDuration(),
                            tp.getFee()
                    )
            );
        }
        return dtoList;
    }

    @Override
    public Optional<TherapyProgrammeDTO> findByTherapyProgrammeId(String pk) {
        Optional<TherapyProgram> entityOpt = therapyProgrammeDAO.findById(pk);
        return entityOpt.map(tp ->
                new TherapyProgrammeDTO(
                        tp.getProgramId(),
                        tp.getName(),
                        tp.getDuration(),
                        tp.getFee()
                )
        );

    }

    @Override
    public List<TherapyProgrammeDTO> findByTherapyProgrammeName(String name) {
        List<TherapyProgram> entities = therapyProgrammeDAO.findByNameList(name);
        List<TherapyProgrammeDTO> dtoList = new ArrayList<>();
        for (TherapyProgram tp : entities) {
            dtoList.add(
                    new TherapyProgrammeDTO(
                            tp.getProgramId(),
                            tp.getName(),
                            tp.getDuration(),
                            tp.getFee()
                    )
            );
        }
        return dtoList;
    }
}
