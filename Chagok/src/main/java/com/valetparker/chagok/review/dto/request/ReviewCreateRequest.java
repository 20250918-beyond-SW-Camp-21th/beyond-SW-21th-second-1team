package com.valetparker.chagok.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ReviewCreateRequest {
    @NotNull(message = "평점은 필수 값입니다.")
    @DecimalMin(value = "0.5", message = "평점은 최소 0.5 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "평점은 최대 5.0 이하입니다.")
    private final Double rating;
    private final String content;
}
