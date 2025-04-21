package lk.ijse.gdse.smhtc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private TherapySession therapySession;  // Nullable for upfront payments

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    //payment type
    @Column(name = "payment_type",nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private LocalDate paymentDate;
}

