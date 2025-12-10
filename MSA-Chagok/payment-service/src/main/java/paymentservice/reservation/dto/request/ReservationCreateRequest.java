package paymentservice.reservation.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReservationCreateRequest {
    private Long userNo;
    private Long parkinglotId;
    private LocalDateTime startTime;
}
