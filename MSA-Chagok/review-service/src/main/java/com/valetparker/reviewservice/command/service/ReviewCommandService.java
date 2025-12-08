package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.repository.JpaReviewCommandRepository;
import com.valetparker.reviewservice.common.entity.Review;
import com.valetparker.reviewservice.common.exception.BusinessException;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {

    private final JpaReviewCommandRepository jpaReviewCommandRepository;

    @Transactional
    public Long createReview(ReviewCreateRequest request, Long reservationId) {
//        Reservation reservation = reservationRepository.findByReservationId(reservationId)
//                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다. reservationId=" + reservationId));
//
//        Long userNo = reservation.getUserNo();
//        Long parkinglotId = reservation.getParkinglotId();
//
//        User user = userRepository.findByUserNo(userNo)
//                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userNo=" + userNo));
//
//        ParkingLot parkingLot = parkinglotRepository.findById(parkinglotId)
//                .orElseThrow(() -> new IllegalArgumentException("주차장 정보를 찾을 수 없습니다. parkinglotId=" + parkinglotId));
//
//        Review newReview = Review.create(
//                request.getRating(),
//                request.getContent(),
//                user,
//                parkingLot,
//                reservation
//        );
//
//        Review saved = jpaReviewCommandRepository.save(newReview);
//        return saved.getReviewId();
        return 1L;
    }

    @Transactional
    public void updateReview(ReviewUpdateRequest request, Long reviewId) {
        Review review = jpaReviewCommandRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        review.updateReview(request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        jpaReviewCommandRepository.deleteById(reviewId);
    }
}