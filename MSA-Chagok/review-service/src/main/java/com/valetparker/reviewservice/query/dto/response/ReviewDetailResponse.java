package com.valetparker.reviewservice.query.dto.response;

import com.valetparker.reviewservice.common.dto.ReviewDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDetailResponse {

    private final ReviewDto reviewDto;

}