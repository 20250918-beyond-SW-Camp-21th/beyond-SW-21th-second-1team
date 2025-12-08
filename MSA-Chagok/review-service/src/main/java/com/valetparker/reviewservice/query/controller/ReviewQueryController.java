package com.valetparker.reviewservice.query.controller;

import com.valetparker.reviewservice.common.dto.ApiResponse;
import com.valetparker.reviewservice.query.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.reviewservice.query.dto.response.ReviewDetailResponse;
import com.valetparker.reviewservice.query.dto.response.ReviewListResponse;
import com.valetparker.reviewservice.query.service.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewQueryController {

    private final ReviewQueryService reviewQueryService;

    @GetMapping("/mypage/reservations/{reservationId}/review")
    public ResponseEntity<ApiResponse<ReviewDetailResponse>> getReservationReview(@PathVariable Long reservationId) {
        ReviewDetailResponse response = reviewQueryService.getReviewByReservation(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/parkinglots/details/{parkinglotId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getParkingReviews(
            @PathVariable Long parkinglotId,
            ParkinglotReviewSearchRequest request
    ) {
        ReviewListResponse response = reviewQueryService.getReviewsByParkinglot(parkinglotId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
