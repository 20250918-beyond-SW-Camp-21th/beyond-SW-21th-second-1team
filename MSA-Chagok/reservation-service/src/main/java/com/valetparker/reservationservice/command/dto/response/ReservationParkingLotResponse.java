package com.valetparker.reservationservice.command.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationParkingLotResponse {
    private Long parkingLotId;
    private Integer base_time;
    private Integer base_fee;
}
