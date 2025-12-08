package com.valetparker.chagok.review.service;

import com.valetparker.chagok.common.dto.Pagination;
import com.valetparker.chagok.common.exception.BusinessException;
import com.valetparker.chagok.common.exception.ErrorCode;
import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import com.valetparker.chagok.review.domain.Review;
import com.valetparker.chagok.review.dto.ReviewDto;
import com.valetparker.chagok.review.dto.request.ParkinglotReviewSearchRequest;
import com.valetparker.chagok.review.dto.request.ReviewCreateRequest;
import com.valetparker.chagok.review.dto.request.ReviewUpdateRequest;
import com.valetparker.chagok.review.dto.response.ReviewDetailResponse;
import com.valetparker.chagok.review.dto.response.ReviewListResponse;
import com.valetparker.chagok.review.enums.ReviewSortType;
import com.valetparker.chagok.review.repository.ReviewRepository;
import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.user.command.repository.UserRepository;
import com.valetparker.chagok.using.domain.Using;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * ====== 논의 사항 ======
 * 1. ErrorCode 기능별로 필요함 -> 코드 기준 필
 * 2. 리뷰 등록시 3개의 타 엔티티 접근 필요. 이 때 엔티티레포를 사용해도 되는지?
 * ===========================
 * */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkinglotRepository parkinglotRepository;

    @Transactional(readOnly = true)
    public ReviewDetailResponse getReviewByReservation(Long reservationId) {
        Review review = reviewRepository.findByReservation_ReservationId(reservationId)
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

    @Transactional
    public Long createReview(ReviewCreateRequest request, Long reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("예약 정보를 찾을 수 없습니다. reservationId=" + reservationId));

        Long userNo = reservation.getUserNo();
        Long parkinglotId = reservation.getParkinglotId();

        User user = userRepository.findByUserNo(userNo)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userNo=" + userNo));

        ParkingLot parkingLot = parkinglotRepository.findById(parkinglotId)
                .orElseThrow(() -> new IllegalArgumentException("주차장 정보를 찾을 수 없습니다. parkinglotId=" + parkinglotId));

        Review newReview = Review.create(
                request.getRating(),
                request.getContent(),
                user,
                parkingLot,
                reservation
        );

        Review saved = reviewRepository.save(newReview);
        return saved.getReviewId();
    }

    @Transactional
    public void updateReview(ReviewUpdateRequest request, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        review.updateReview(request);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}
