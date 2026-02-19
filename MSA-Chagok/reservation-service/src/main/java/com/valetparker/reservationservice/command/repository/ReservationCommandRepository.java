package com.valetparker.reservationservice.command.repository;

import com.valetparker.reservationservice.common.entity.Reservation;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationCommandRepository extends JpaRepository<Reservation, Long> {
    // 특정 주차장에, 취소되지 않았고, 시간대가 겹치는 예약이 하나라도 있는지 체크
    boolean existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
            Long parkinglotId,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    Reservation findByReservationIdAndIsCanceledFalse(Long reservationId);
}
