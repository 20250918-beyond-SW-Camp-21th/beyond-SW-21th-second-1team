package com.valetparker.reservationservice.command.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ReservationStartRequest {
    private Long parkinglotId;
    private Long reservationId;
    private String updateTime;
}
