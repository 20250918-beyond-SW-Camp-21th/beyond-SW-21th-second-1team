package com.valetparker.reservationservice.command.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReservationUpdateRequest {
    private Long parkingLotId;
    private String timeUpdate;
}
