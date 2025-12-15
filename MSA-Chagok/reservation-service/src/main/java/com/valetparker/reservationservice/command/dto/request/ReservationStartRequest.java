package com.valetparker.reservationservice.command.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationStartRequest {
    private Long parkinglotId;
    private Long reservationId;
    private String updateTime;
}
