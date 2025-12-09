package com.valetparker.chagok.reservation.repository;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.dto.response.ReservationResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findByReservationId(Long reservationId);

    List<Reservation> findByUserNoOrderByCreatedAtDesc(Long userNo);

    // 특정 주차장에, 취소되지 않았고, 시간대가 겹치는 예약이 하나라도 있는지 체크
    boolean existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
            Long parkinglotId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    // partnerOrderId 가 이미 존재하는지 체크
    boolean existsByPartnerOrderId(String partnerOrderId);
}
