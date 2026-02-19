package com.valetparker.reservationservice.command.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationEndRequest {
    private Long parkinglotId;
    private Long reservationId;
}
