package paymentservice.review.dto;

import com.valetparker.chagok.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
/*
 * ====== 논의 사항 ======
 * 1. 다른분들 Dto 완성할 때까지 Dto 주석처리..
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
