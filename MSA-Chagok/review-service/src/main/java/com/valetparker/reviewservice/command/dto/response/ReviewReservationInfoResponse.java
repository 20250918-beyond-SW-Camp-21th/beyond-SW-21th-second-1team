package com.valetparker.reviewservice.command.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewReservationInfoResponse {
    private Long reservationId;
    private Long userNo;
    private Long parkinglotId;
}
