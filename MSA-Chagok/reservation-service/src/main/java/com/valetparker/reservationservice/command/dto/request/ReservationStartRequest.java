package com.valetparker.reservationservice.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationStartRequest {
    private Long parkinglotId;
    private Long reservationId;
    private String updateTime;
}
