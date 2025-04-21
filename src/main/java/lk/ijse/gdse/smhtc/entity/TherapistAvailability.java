package lk.ijse.gdse.smhtc.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Table(name = "therapist_availability")
public class TherapistAvailability {
    @Id
    private String availabilityId;

    @ManyToOne
    @JoinColumn(name = "therapist_id", nullable = false)
    private Therapist therapist;
//////////////////////////////////////////////////////////
    @Column(name = "session_Id", nullable = false)
    private String sessionId;

    @Column(nullable = false)
    private String availability;  // "Available", "Unavailable"
/////////////////////////////////////////////////////////
    @Column(nullable = false)
    private LocalDate availableDate;

    @Column(nullable = false)
    private LocalTime availableTime;
}
