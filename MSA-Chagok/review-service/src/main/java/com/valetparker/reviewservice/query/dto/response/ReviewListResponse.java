package com.valetparker.reviewservice.query.dto.response;

import com.valetparker.reviewservice.common.dto.Pagination;
import com.valetparker.reviewservice.common.dto.ReviewDto;
import com.valetparker.reviewservice.common.enums.ReviewSortType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReviewListResponse {
    private final List<ReviewDto> reviewDtoList;
    private final Pagination pagination;
    private final ReviewSortType sortType;
}