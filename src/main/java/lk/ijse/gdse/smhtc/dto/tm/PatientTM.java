package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientTM {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String medicalHistory;
}
