package com.valetparker.chagok.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCreateRequest {

    private Long userNo;
    private Long parkinglotId;
    private String partnerOrderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
