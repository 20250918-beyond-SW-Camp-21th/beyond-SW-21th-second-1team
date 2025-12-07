package com.valetparker.chagok.review.service;

import com.valetparker.chagok.common.dto.Pagination;
import com.valetparker.chagok.common.exception.BusinessException;
import com.valetparker.chagok.common.exception.ErrorCode;
import com.valetparker.chagok.review.domain.Review;
import com.valetparker.chagok.review.dto.ReviewDto;
import com.valetparker.chagok.review.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.chagok.review.dto.response.ReviewDetailResponse;
import com.valetparker.chagok.review.dto.response.ReviewListResponse;
import com.valetparker.chagok.review.enums.ReviewSortType;
import com.valetparker.chagok.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * ====== 논의 사항 ======
 * ErrorCode 기능별로 나눠야 할지?
 * ===========================
 * */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

//    @Transactional(readOnly = true)
//    public ReviewDetailResponse getReviewByUsing(Long usingId) {
//        Review review = reviewRepository.findByUsing_UsingId(usingId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
//        ReviewDto reviewDto = ReviewDto.from(review);
//
//        return ReviewDetailResponse.builder()
//                .reviewDto(reviewDto)
//                .build();
//    }

    @Transactional(readOnly = true)
    public ReviewListResponse getReviewsByParkinglot(Long parkinglotId, ParkinglotReviewSearchRequest request) {

        int page = request.getPage();  // 1-based
        int size = request.getSize();
        ReviewSortType sortType = request.getSort();

        Sort sort = switch (sortType) {
            case LATEST      -> Sort.by(Sort.Direction.DESC, "reviewCreatedAt");
            case RATING_DESC -> Sort.by(Sort.Direction.DESC, "rating");
            case RATING_ASC  -> Sort.by(Sort.Direction.ASC, "rating");
        };

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Review> reviewPage = reviewRepository.findByParkinglot_ParkinglotId(parkinglotId, pageable);
        List<ReviewDto> reviewDtoList = reviewPage.getContent().stream()
                .map(ReviewDto::from)
                .toList();

        Pagination pagination = Pagination.builder()
                .currentPage(page)
                .totalPages(reviewPage.getTotalPages())
                .totalItems(reviewPage.getTotalElements())
                .build();

        return ReviewListResponse.builder()
                .reviewDtoList(reviewDtoList)
                .pagination(pagination)
                .sortType(sortType)
                .build();
    }
}
