package com.valetparker.reservationservice.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationCreateRequest {
    private Long parkingLotId;
    private String startTime;
}
