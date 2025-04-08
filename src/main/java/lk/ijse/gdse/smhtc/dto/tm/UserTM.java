package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserTM {
    private String id;
    private String username;
    private String password;
    private String role; //Admin, Receptionist
    private String email;
}
