package com.valetparker.reviewservice.query.repository;

import com.valetparker.reviewservice.common.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewQueryRepository extends JpaRepository<Review, Long> {
    // 예약 정보 별 리뷰 조회
    Optional<Review> findByReservation_ReservationId(Long reservationId);

    // 주차장 별 리뷰 조회
    Page<Review> findByParkinglot_ParkinglotId(Long parkinglotId, Pageable pageable);

}
