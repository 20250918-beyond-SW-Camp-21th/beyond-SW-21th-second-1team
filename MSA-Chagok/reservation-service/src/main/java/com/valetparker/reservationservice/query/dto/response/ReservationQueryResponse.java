package com.valetparker.reservationservice.query.dto.response;

import com.valetparker.reservationservice.common.dto.ReservationDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationQueryResponse {

    private final ReservationDto reservationDto;
}
