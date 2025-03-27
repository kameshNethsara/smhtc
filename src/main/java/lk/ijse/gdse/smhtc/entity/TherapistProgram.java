package lk.ijse.gdse.smhtc.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Table(name = "therapist_program")
public class TherapistProgram {
//    @Id
//    private String id;

    @EmbeddedId
    private TherapistProgramId id;

    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;
}
