package com.valetparker.reservationservice.command.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ReservationCreateRequest {
    private Long parkingLotId;
    private String startTime;
}
