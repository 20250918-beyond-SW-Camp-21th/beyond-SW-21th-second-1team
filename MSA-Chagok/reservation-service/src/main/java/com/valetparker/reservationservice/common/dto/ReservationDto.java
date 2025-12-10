package com.valetparker.reservationservice.common.dto;

import com.valetparker.reservationservice.common.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationDto {

    private long reservationId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isCanceled;
    private LocalDateTime createdAt;
    private long userNo;
    private long parkinglotId;

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .reservationId(reservation.getReservationId())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .isCanceled(reservation.getIsCanceled())
                .createdAt(reservation.getCreatedAt())
                .userNo(reservation.getUserNo())
                .parkinglotId(reservation.getParkinglotId())
                .build();
    }
}