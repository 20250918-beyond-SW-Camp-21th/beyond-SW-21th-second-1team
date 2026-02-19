package com.valetparker.reviewservice.query.dto.request;

import com.valetparker.reviewservice.common.enums.ReviewSortType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkinglotReviewSearchRequest {
    private Integer page = 1;
    private Integer size = 10;
    private ReviewSortType sort = ReviewSortType.LATEST;

//    public int getOffset() {
//        return (page - 1) * size;
//    }
    public int getOffset() {
        int validPage = (page == null || page < 1) ? 1 : page;
        return (validPage - 1) * size;
    }


    public int getLimit() {
        return size;
    }
}