package com.valetparker.reservationservice.command.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCommandResponse {
    private Long reservationId;
}
