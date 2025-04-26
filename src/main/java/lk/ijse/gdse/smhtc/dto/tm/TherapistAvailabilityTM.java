package lk.ijse.gdse.smhtc.dto.tm;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TherapistAvailabilityTM {
    private String availabilityId;
    private String therapistId;
    private String sessionId;
    private String availability;
    private LocalDate availableDate;
    private LocalTime availableTime;

}
