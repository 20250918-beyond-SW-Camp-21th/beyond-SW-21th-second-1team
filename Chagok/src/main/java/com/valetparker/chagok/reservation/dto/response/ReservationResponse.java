package com.valetparker.chagok.reservation.dto.response;

import com.valetparker.chagok.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationResponse {
    private Long reservationId;
}
