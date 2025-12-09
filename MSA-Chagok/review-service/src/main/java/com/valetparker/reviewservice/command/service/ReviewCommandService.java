package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.client.ReservationClient;
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
    private final ReservationClient reservationClient;


    @Transactional
    public Long createReview(ReviewCreateRequest request, Long reservationId) {

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