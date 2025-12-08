package com.valetparker.reviewservice.command.controller;

import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.dto.response.ReviewCommandResponse;
import com.valetparker.reviewservice.command.service.ReviewCommandService;
import com.valetparker.reviewservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class ReviewCommandController {

    private final ReviewCommandService reviewCommandService;

    @PostMapping("/reservations/{reservationId}/review")
    public ResponseEntity<ApiResponse<ReviewCommandResponse>> registerReview(
            @PathVariable Long reservationId,
            @RequestBody ReviewCreateRequest request
    ) {
        Long reviewId = reviewCommandService.createReview(request, reservationId);
        ReviewCommandResponse response = ReviewCommandResponse.builder()
                .reviewId(reviewId)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/mypage/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> modifyReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequest request
    ) {
        reviewCommandService.updateReview(request, reviewId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/mypage/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @PathVariable Long reviewId
    ) {
        reviewCommandService.deleteReview(reviewId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
