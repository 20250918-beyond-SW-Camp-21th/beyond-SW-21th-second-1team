package com.valetparker.reservationservice.command.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationCommandRequest {
    private Long parkingLotId;
    private String time;
}
