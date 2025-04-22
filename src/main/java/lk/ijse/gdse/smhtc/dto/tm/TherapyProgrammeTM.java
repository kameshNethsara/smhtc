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

    public TherapyProgrammeTM(String id, String name, String duration, BigDecimal fee, Object o) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }
}
