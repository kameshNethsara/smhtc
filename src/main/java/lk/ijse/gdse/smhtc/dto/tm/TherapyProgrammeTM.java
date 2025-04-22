package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TherapyProgrammeTM {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
}
