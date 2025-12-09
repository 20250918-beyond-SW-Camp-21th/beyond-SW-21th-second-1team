package com.valetparker.reviewservice.query.service;

import com.valetparker.reviewservice.common.dto.Pagination;
import com.valetparker.reviewservice.common.dto.ReviewDto;
import com.valetparker.reviewservice.common.entity.Review;
import com.valetparker.reviewservice.common.enums.ReviewSortType;
import com.valetparker.reviewservice.common.exception.BusinessException;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import com.valetparker.reviewservice.query.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.reviewservice.query.dto.response.ReviewDetailResponse;
import com.valetparker.reviewservice.query.dto.response.ReviewListResponse;
import com.valetparker.reviewservice.query.repository.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewQueryRepository reviewQueryRepository;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByReservation(Long reservationId) {
        Review review = reviewQueryRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        ReviewDto reviewDto = ReviewDto.from(review);

        return ReviewDetailResponse.builder()
                .reviewDto(reviewDto)
                .build();
    }

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

        Page<Review> reviewPage = reviewQueryRepository.findByParkinglotId(parkinglotId, pageable);
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
