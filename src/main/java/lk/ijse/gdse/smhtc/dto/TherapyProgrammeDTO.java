package lk.ijse.gdse.smhtc.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TherapyProgrammeDTO {
    private String id;
    private String name;
    private String duration;
    private BigDecimal fee;
}
