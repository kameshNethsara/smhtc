package lk.ijse.gdse.smhtc.bo.custom;

import lk.ijse.gdse.smhtc.bo.SuperBO;
import lk.ijse.gdse.smhtc.dto.TherapistProgramDTO;
import lk.ijse.gdse.smhtc.entity.Therapist;
import lk.ijse.gdse.smhtc.entity.TherapistProgram;
import lk.ijse.gdse.smhtc.entity.TherapistProgramId;
import lk.ijse.gdse.smhtc.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TherapistProgramBO extends SuperBO {

    public boolean saveTherapistProgram(String therapistId, String programId);
    public boolean updateTherapistProgram(String therapistId, String programId);
    public boolean deleteTherapistProgram(String therapistId, String programId);
    public TherapistProgramDTO findById(String therapistId, String programId);
    public List<TherapistProgramDTO> getAllTherapistPrograms();
    public List<TherapistProgramDTO> getTherapistProgramsByTherapist(String id);
    public List<TherapistProgramDTO> findByProgramName(String name);
    public List<TherapistProgramDTO> getTherapistProgramsByTherapistId(String therapistId);
    public List<TherapistProgramDTO> getTherapistProgramsByProgramId(String programId);
    public List<TherapistProgramDTO> findIdList(String id);
}
