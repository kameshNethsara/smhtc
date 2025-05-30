package lk.ijse.gdse.smhtc.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDTO {
    private String paymentId;
    private String patientId;
    private String therapyProgramId;
    private String therapySessionId; // Nullable for upfront payments
    private BigDecimal amount;
    private String paymentType;
    private LocalDate paymentDate;


}
