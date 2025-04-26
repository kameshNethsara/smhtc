package lk.ijse.gdse.smhtc.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientProgrammeDTO {
    private String patientId;
//    private String patientName;
    private String programId;
//    private String programName;
    private String paymentId;
    private BigDecimal fee;
    private BigDecimal balance;
    private String registrationDate;
}
