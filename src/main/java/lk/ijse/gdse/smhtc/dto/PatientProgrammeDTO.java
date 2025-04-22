package lk.ijse.gdse.smhtc.dto;

import lombok.*;

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
    private String registrationDate;
}
