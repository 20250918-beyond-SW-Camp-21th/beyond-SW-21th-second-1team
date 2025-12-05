package com.valetparker.chagok.review.dto.response;

import com.valetparker.chagok.review.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDetailResponse {

    private final ReviewDto reviewDto;

}
