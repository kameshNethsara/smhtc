package lk.ijse.gdse.smhtc.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String role; //Admin, Receptionist
    private String email;

    public String getEmail(String text) {
        return email;
    }
}
