package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TherapistTM {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String specialization;
}
