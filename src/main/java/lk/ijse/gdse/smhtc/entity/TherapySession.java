package lk.ijse.gdse.smhtc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Table(name = "therapy_session")
public class TherapySession {
    @Id
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    @Column(nullable = false)
    private LocalDate sessionDate;

    @Column(nullable = false)
    private LocalTime sessionTime;

    @Column(nullable = false)
    private String status;  // "Scheduled", "Completed", "Cancelled"

    @OneToMany(mappedBy = "therapySession", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments;
}