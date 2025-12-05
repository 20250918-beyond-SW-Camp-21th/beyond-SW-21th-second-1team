package com.valetparker.chagok.reservation.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {

    private long reservationId;
    private String partnerOrderId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCanceled;
    private LocalDateTime createdAt;
    private long userNo;
    private long parkinglotId;
}
