package com.valetparker.reservationservice.query.repository;

import com.valetparker.reservationservice.common.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationQueryRepository extends JpaRepository<Reservation,Long> {

    // 예약 조회
    Optional<Reservation> findByReservationId(Long ReservationId);
    // 예약 전체 최신순 조회
    List<Reservation> findAllByUserNoOrderByCreatedAtDesc(Long userNo);
}
