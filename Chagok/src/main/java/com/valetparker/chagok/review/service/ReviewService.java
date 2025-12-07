package com.valetparker.chagok.review.service;

import com.valetparker.chagok.review.domain.Review;
import com.valetparker.chagok.review.dto.ReviewDto;
import com.valetparker.chagok.review.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.chagok.review.dto.response.ReviewDetailResponse;
import com.valetparker.chagok.review.dto.response.ReviewListResponse;
import com.valetparker.chagok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByUsing(Long usingId) {
        Review review = reviewRepository.findByUsingId(usingId)
                .orElseThrow(() ->)

        return null;
    }

    @Transactional
    public ReviewListResponse getReviewsByParkinglot(Long parkinglotId, ParkinglotReviewSearchRequest request) {
        return null;
    }
}
