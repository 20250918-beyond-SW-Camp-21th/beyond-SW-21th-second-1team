package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.client.ReservationClient;
import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.dto.response.ReviewReservationInfoResponse;
import com.valetparker.reviewservice.command.repository.JpaReviewCommandRepository;
import com.valetparker.reviewservice.common.entity.Review;
import com.valetparker.reviewservice.common.exception.BusinessException;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import com.valetparker.reviewservice.common.model.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewCommandService {

    private final JpaReviewCommandRepository jpaReviewCommandRepository;
    private final ReservationClient reservationClient;


    @Transactional
    public Long createReview(ReviewCreateRequest request, Long reservationId) {

        Double rating = request.getRating();
        if (rating == null || rating < 1 || rating > 5) {
            throw new BusinessException(ErrorCode.INVALID_RATING);
        }


        ReviewReservationInfoResponse reservation = reservationClient
                .getReservation(reservationId)
                .getData();
        Long userNo = reservation.getUserNo();
        Long parkinglotId = reservation.getParkinglotId();

        if (userNo == null || parkinglotId == null || reservationId == null) {
            throw new BusinessException(ErrorCode.INVALID_REVIEW_REQUEST);
        }

        if (!isValidUser(userNo)) {
            throw new BusinessException(ErrorCode.REVIEW_ACCESS_DENIED);
        }

        Review newReview = Review.create(
                request.getRating(),
                request.getContent(),
                reservation.getUserNo(),
                reservation.getParkinglotId(),
                reservation.getReservationId()
        );

        Review saved = jpaReviewCommandRepository.save(newReview);
        if (saved == null) {
            throw new BusinessException(ErrorCode.REVIEW_CREATE_FAILED);
        }
        return saved.getReviewId();
    }

    @Transactional
    public void updateReview(ReviewUpdateRequest request, Long reviewId) {
        Review review = jpaReviewCommandRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
        Long userNo = review.getWriterId();
        if (!isValidUser(userNo)) {
            throw new BusinessException(ErrorCode.REVIEW_ACCESS_DENIED);
        }
        if (review.updateReview(request) != 1) {
            throw new BusinessException(ErrorCode.REVIEW_UPDATE_FAILED);
        }
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = jpaReviewCommandRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.REVIEW_NOT_FOUND));
        if (!isValidUser(review.getWriterId())) {
            throw new BusinessException(ErrorCode.REVIEW_ACCESS_DENIED);
        }
        jpaReviewCommandRepository.deleteById(reviewId);
        if (jpaReviewCommandRepository.findById(reviewId).isPresent()) {
            throw new BusinessException(ErrorCode.REVIEW_DELETE_FAILED);
        }
    }

//    private boolean isValidUser(Long validUserNo) {
//        CustomUser user = (CustomUser) SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal();
//        Long accessUserNo = user.getUserNo();
//        if (accessUserNo != validUserNo) {
//            return false;
//        }
//        return true;
//    }

    private boolean isValidUser(Long userNo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUser customUser)) {
            return false;
        }
        return Objects.equals(customUser.getUserNo(), userNo);
    }

}