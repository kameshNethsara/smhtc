package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

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
    private LocalDate registerDate;
}
