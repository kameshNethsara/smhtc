package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomPatientProgrammeTM {
    private String patientId;
    private String programmeId;
    private String paymentId;
    private BigDecimal fee;
    private BigDecimal balance;
    private LocalDate registerDate;
}
