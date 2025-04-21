package lk.ijse.gdse.smhtc.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String medicalHistory;
}
