package com.valetparker.chagok.review.dto.request;

import com.valetparker.chagok.review.enums.ReviewSortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkinglotReviewSearchRequest {
    private Integer page = 1;
    private Integer size = 10;
    private ReviewSortType sort = ReviewSortType.LATEST;

    public int getOffset() {
        return (page - 1) * size;
    }

    public int getLimit() {
        return size;
    }
}
