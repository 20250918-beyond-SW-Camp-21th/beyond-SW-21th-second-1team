package com.valetparker.chagok.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReservationCreateRequest {
    private Long userNo;
    private Long parkinglotId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
