package com.valetparker.chagok.review.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
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
* 1. 주차장 상세 조회 페이지에서 주차장별 리뷰 조회 페이지로 넘어오므로, 주차장 상세조회페이지 url?을 같게 맞춰야한다.
* 2. 내 개인 리뷰만 볼때 이용정보를 통해서만 볼지, 아니면 <내 리뷰 보기> 버튼이 있다치고 내 리뷰들 리스트만 쭉 볼 수도 있게 할
* 3. 마이페이지 있나??
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
    @GetMapping("/mypage/{userId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getMyAllReviews(@PathVariable Long userId) {
        ReviewListResponse response = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/mypage/{usingId}/{review}")
    public ResponseEntity<ApiResponse<ReviewDetailResponse>> getMyOneReview(@PathVariable Long usingId, @PathVariable Long reviewId) {
        ReviewDetailResponse response = reviewService.getReviewByUsing(reviewId, usingId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


    /*
    * 주차장별 리뷰 조회
    * 1. 주차장 목록이 있고, 그 중 하나를 골라 상세조회페이지로 넘어간다.
    * 2. 이 때 특정 주차장에 대한 리뷰가 모여있는 리뷰 페이지로 넘어갈 수 있다.
    * 3. 이는 오로지 조회목적이다. 개인별 리뷰 조회와 달리 등록/수정/삭제가 되지 않는다.
    * */
    @GetMapping("/parkinglots/details/{parkinglotId}/reviews")
    public ResponseEntity<ApiResponse<ReviewListResponse>> getReviews(@PathVariable Long parkinglotId) {
        ReviewListResponse response = reviewService.getReviewsByParkinglot(parkinglotId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
