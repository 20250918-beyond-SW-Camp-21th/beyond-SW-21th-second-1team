package com.valetparker.chagok.reservation.dto.response;

import com.valetparker.chagok.reservation.dto.ReservationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationListResponse {
    private ReservationDto reservationDto;
}
