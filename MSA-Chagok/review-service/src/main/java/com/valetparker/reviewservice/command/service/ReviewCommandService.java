package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.client.ReservationClient;
import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.dto.response.ReviewReservationInfoResponse;
import com.valetparker.reviewservice.command.repository.JpaReviewCommandRepository;
import com.valetparker.reviewservice.common.entity.Review;
import com.valetparker.reviewservice.common.exception.BusinessException;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {

    private final JpaReviewCommandRepository jpaReviewCommandRepository;
    private final ReservationClient reservationClient;


    @Transactional
    public Long createReview(ReviewCreateRequest request, Long reservationId) {

        ReviewReservationInfoResponse reservation = reservationClient
                .getReservation(reservationId)
                .getData();

        Review newReview = Review.create(
                request.getRating(),
                request.getContent(),
                reservation.getUserNo(),
                reservation.getParkinglotId(),
                reservation.getReservationId()
        );

        Review saved = jpaReviewCommandRepository.save(newReview);
        return saved.getReviewId();
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