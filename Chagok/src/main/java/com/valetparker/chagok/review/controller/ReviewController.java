package com.valetparker.chagok.review.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.review.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.chagok.review.dto.response.ReviewDetailResponse;
import com.valetparker.chagok.review.dto.response.ReviewListResponse;
import com.valetparker.chagok.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
* ====== 논의 사항 ======
* ===========================
* */

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /*
    * 개인별 리뷰 조회
    * 1. 마이페이지를 통해 이용 정보를 리스트 형식으로 확인할 수 있다.
    * 2. 이 때 각 이용 정보들에 본인이 작성한 리뷰가 있을 경우 이를 조회할 수 있다.
    * 3. 이용정보 별로 본인이 작성한 리뷰를 볼 수 있는 것이다.
    * */
//    @GetMapping("/mypage/{usingId}/review")
//    public ResponseEntity<ApiResponse<ReviewDetailResponse>> getUsingReview(@PathVariable Long usingId) {
//        ReviewDetailResponse response = reviewService.getReviewByUsing(usingId);
//        return ResponseEntity.ok(ApiResponse.success(response));
//    }


    /*
    * 주차장별 리뷰 조회
    * 1. 주차장 목록이 있고, 그 중 하나를 골라 상세조회페이지로 넘어간다.
    * 2. 이 때 특정 주차장에 대한 리뷰가 모여있는 리뷰 페이지로 넘어갈 수 있다.
    * 3. 이는 오로지 조회목적이다. 개인별 리뷰 조회와 달리 등록/수정/삭제가 되지 않는다.
    * */
    // 리뷰 조회 - 최신순/별점높은순/별점낮은순 조회 가능
    @GetMapping("/parkinglots/details/{parkinglotId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getParkingReviews(
            @PathVariable Long parkinglotId,
            ParkinglotReviewSearchRequest request
    ) {
        ReviewListResponse response = reviewService.getReviewsByParkinglot(parkinglotId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


}
