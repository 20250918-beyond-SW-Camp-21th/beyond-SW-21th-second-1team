package com.valetparker.reservationservice.query.dto.response;

import com.valetparker.reservationservice.common.dto.ReservationDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReservationListResponse {
    private final List<ReservationDto> reservationDtoList;
}
