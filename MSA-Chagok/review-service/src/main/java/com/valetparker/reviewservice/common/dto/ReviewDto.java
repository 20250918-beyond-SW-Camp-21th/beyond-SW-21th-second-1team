package com.valetparker.reviewservice.common.dto;

import com.valetparker.reviewservice.common.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/*
 * ====== 논의 사항 ======
 * ===========================
 * */
@Getter
@Setter
@Builder
public class ReviewDto {

    private Long reviewId;
    private Double rating;
    private String content;
    private LocalDateTime reviewCreatedAt;
    private LocalDateTime reviewModifiedAt;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .content(review.getContent())
                .reviewCreatedAt(review.getReviewCreatedAt())
                .reviewModifiedAt(review.getReviewModifiedAt())
                .build();
    }
}