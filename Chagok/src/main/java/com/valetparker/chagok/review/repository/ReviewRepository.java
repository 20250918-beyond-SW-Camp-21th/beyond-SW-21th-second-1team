package com.valetparker.chagok.review.repository;

import com.valetparker.chagok.review.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long reviewId);

    // 이용 정보 별 리뷰 조회
    Optional<Review> findByUsingId(Long usingId);

    // 주차장 별 리뷰 조회
    List<Review> findByParkinglotOrderByReviewCreatedAtDesc(Long parkinglotId);

    List<Review> findByParkinglotOrderByRating(Long parkinglotId);

    List<Review> findByParkinglotOrderByRatingDesc(Long parkinglotId);

    void deleteById(Long reviewId);

}
