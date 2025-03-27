package lk.ijse.gdse.smhtc.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Table(name = "therapy_program")
public class TherapyProgram {
    @Id
    private String programId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String duration;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @OneToMany(mappedBy = "therapyProgram", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments;
}
