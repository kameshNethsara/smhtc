package lk.ijse.gdse.smhtc.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TherapistDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String specialization;
}
