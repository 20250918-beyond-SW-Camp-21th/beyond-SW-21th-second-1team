package com.valetparker.chagok.review.dto.response;

import com.valetparker.chagok.common.dto.Pagination;
import com.valetparker.chagok.review.dto.ReviewDto;
import com.valetparker.chagok.review.enums.ReviewSortType;
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
