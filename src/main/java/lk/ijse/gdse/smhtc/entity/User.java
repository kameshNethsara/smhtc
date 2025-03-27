package lk.ijse.gdse.smhtc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

@Table(name = "user")
public class User {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; //Admin, Receptionist

    private String email;

    public void setPassword(String password) {
//        this.password = new BCryptPasswordEncoder().encode(password);

    }

}
