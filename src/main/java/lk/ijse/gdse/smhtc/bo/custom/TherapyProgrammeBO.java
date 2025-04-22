package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.List;
import java.util.Optional;

public interface TherapyProgrammeBO {
    public String getNextId();
    public boolean save(TherapyProgrammeDTO therapyProgrammeDTO);
    public boolean update(TherapyProgrammeDTO therapyProgrammeDTO);
    public boolean delete(String pk);
    public List<TherapyProgrammeDTO> getAll();
    public Optional<TherapyProgrammeDTO> findById(String pk);
    public Optional<String> getLastPK();
    /////////////////////////////////////////
    public List<TherapyProgrammeDTO> findByName(String name);
}
