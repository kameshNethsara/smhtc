package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.dto.TherapyProgrammeDTO;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.List;
import java.util.Optional;

public interface TherapyProgrammeBO {
    public String getNextTherapyProgrammeId();
    public boolean saveTherapyProgramme(TherapyProgrammeDTO therapyProgrammeDTO);
    public boolean updateTherapyProgramme(TherapyProgrammeDTO therapyProgrammeDTO);
    public boolean deleteTherapyProgramme(String pk);
    public List<TherapyProgrammeDTO> getAllTherapyProgrammes();
    public Optional<TherapyProgrammeDTO> findByTherapyProgrammeId(String pk);
    /////////////////////////////////////////
    public List<TherapyProgrammeDTO> findByTherapyProgrammeName(String name);
}
