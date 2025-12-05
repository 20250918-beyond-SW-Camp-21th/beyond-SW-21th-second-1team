package com.valetparker.chagok.reservation.dto.response;

import com.valetparker.chagok.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {
    private ReservationDto reservationDto;
}
