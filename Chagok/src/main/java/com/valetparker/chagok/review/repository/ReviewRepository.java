package com.valetparker.chagok.review.repository;

import com.valetparker.chagok.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long reviewId);

    // 예약 정보 별 리뷰 조회
    Optional<Review> findByReservation_ReservationId(Long reservationId);

    // 주차장 별 리뷰 조회
    Page<Review> findByParkinglot_ParkinglotId(Long parkinglotId, Pageable pageable);

    void deleteById(Long reviewId);

}
