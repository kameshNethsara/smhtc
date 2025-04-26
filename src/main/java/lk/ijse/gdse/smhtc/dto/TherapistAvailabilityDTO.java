package lk.ijse.gdse.smhtc.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TherapistAvailabilityDTO {
    private String availabilityId;
    private String therapistId;
    private String sessionId;
    private String availability;
    private LocalDate availableDate;
    private LocalTime availableTime;

}
