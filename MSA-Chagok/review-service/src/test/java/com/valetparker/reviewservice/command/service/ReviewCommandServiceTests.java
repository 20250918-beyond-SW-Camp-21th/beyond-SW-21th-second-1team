package com.valetparker.reviewservice.command.service;

import com.valetparker.reviewservice.command.client.ReservationClient;
import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.dto.response.ReviewReservationInfoResponse;
import com.valetparker.reviewservice.command.repository.JpaReviewCommandRepository;
import com.valetparker.reviewservice.common.dto.ApiResponse;
import com.valetparker.reviewservice.common.entity.Review;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import com.valetparker.reviewservice.common.model.CustomUser;
import com.valetparker.reviewservice.common.exception.BusinessException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewCommandServiceTests {

    @Mock
    private JpaReviewCommandRepository repository;

    @Mock
    private ReservationClient reservationClient;

    @InjectMocks
    private ReviewCommandService commandService;

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

    private ReviewCreateRequest buildRequest(Double rating) {
        return ReviewCreateRequest.builder()
                .rating(rating)
                .content("좋은 주차장이었어요.")
                .build();
    }

    private ReviewReservationInfoResponse buildReservation(Long reservationId, Long userNo, Long parkinglotId) {
        return ReviewReservationInfoResponse.builder()
                .reservationId(reservationId)
                .userNo(userNo)
                .parkinglotId(parkinglotId)
                .build();
    }

    private <T> ApiResponse<T> apiResponse(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .build();
    }

    @Test
    @DisplayName("Ok : 리뷰 정상 등록")
    void createReview_Ok() {
        // given
        Long reservationId = 1L;
        Long userNo = 10L;
        Long parkinglotId = 100L;

        ReviewCreateRequest request = buildRequest(4.0);

        ReviewReservationInfoResponse reservation = buildReservation(reservationId, userNo, parkinglotId);
        when(reservationClient.getReservation(reservationId))
                .thenReturn(apiResponse(reservation));

        Review saved = Review.create(
                request.getRating(),
                request.getContent(),
                userNo,
                parkinglotId,
                reservationId
        );
        // 저장 후 ID가 세팅된다고 가정
        ReflectionTestUtils.setField(saved, "reviewId", 999L);
        when(repository.save(any(Review.class)))
                .thenReturn(saved);

        // SecurityContext에 현재 로그인 유저(userNo 동일) 세팅
        CustomUser customUser = CustomUser.builder()
                .userNo(userNo)
                .email("test@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        Long resultId = commandService.createReview(request, reservationId);

        // then
        assertThat(resultId).isEqualTo(999L);
        verify(repository).save(any(Review.class));
    }

    @Test
    @DisplayName("NotOk : 예약 이용자와 리뷰 등록자 불일치")
    void createReview_AccessDenied() {
        // given
        Long reservationId = 1L;
        Long ownerUserNo = 10L;     // 예약의 주인
        Long currentUserNo = 99L;   // 로그인한 사용자(다른 사람)
        Long parkinglotId = 100L;

        ReviewCreateRequest request = buildRequest(4.0);

        ReviewReservationInfoResponse reservation = buildReservation(reservationId, ownerUserNo, parkinglotId);
        when(reservationClient.getReservation(reservationId))
                .thenReturn(apiResponse(reservation));

        // SecurityContext에 다른 userNo 세팅 → isValidUser(userNo) = false가 되어야 함
        CustomUser customUser = CustomUser.builder()
                .userNo(currentUserNo)
                .email("other@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.createReview(request, reservationId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_ACCESS_DENIED);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("NotOk : 범위 밖의 별점 입력")
    void createReview_Invalid() {
        // given
        Long reservationId = 1L;

        // rating이 0이거나 6 같은 범위 밖 값
        ReviewCreateRequest request = buildRequest(0.0);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.createReview(request, reservationId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_RATING);
        // reservationClient, repository 호출 없어야 함
        verifyNoInteractions(reservationClient);
        verifyNoInteractions(repository);
    }

    // ✅ save()에서 null 반환 → REVIEW_CREATE_FAILED
    @Test
    @DisplayName("NotOk : 리뷰 등록 실")
    void createReview_CreateFailed() {
        // given
        Long reservationId = 1L;
        Long userNo = 10L;
        Long parkinglotId = 100L;

        ReviewCreateRequest request = buildRequest(4.0);

        ReviewReservationInfoResponse reservation = buildReservation(reservationId, userNo, parkinglotId);
        when(reservationClient.getReservation(reservationId))
                .thenReturn(apiResponse(reservation));

        // SecurityContext에 정상 유저 세팅
        CustomUser customUser = CustomUser.builder()
                .userNo(userNo)
                .email("test@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // save가 null을 반환하는 비정상 상황을 강제로 만듦
        when(repository.save(any(Review.class)))
                .thenReturn(null);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.createReview(request, reservationId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_CREATE_FAILED);
    }


    // ================== updateReview ==================

    private ReviewUpdateRequest buildUpdateRequest(Double rating, String content) {
        return ReviewUpdateRequest.builder()
                .rating(rating)
                .content(content)
                .build();
    }

    @Test
    @DisplayName("Ok : 리뷰 정상 수정")
    void updateReview_Ok() {
        // given
        Long reviewId = 1L;
        Long userNo = 10L;
        Long parkinglotId = 100L;
        Long reservationId = 999L;

        ReviewUpdateRequest request = buildUpdateRequest(5.0, "수정된 내용");

        Review existing = Review.create(
                3.0,
                "원래 내용",
                userNo,
                parkinglotId,
                reservationId
        );
        ReflectionTestUtils.setField(existing, "reviewId", reviewId);

        when(repository.findById(reviewId))
                .thenReturn(Optional.of(existing));

        CustomUser customUser = CustomUser.builder()
                .userNo(userNo)
                .email("test@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        commandService.updateReview(request, reviewId);

        // then
        verify(repository).findById(reviewId);
    }

    @Test
    @DisplayName("NotOk : 리뷰 수정 - 권한 없음")
    void updateReview_AccessDenied() {
        // given
        Long reviewId = 1L;
        Long ownerUserNo = 10L;
        Long currentUserNo = 99L;
        Long parkinglotId = 100L;
        Long reservationId = 999L;

        ReviewUpdateRequest request = buildUpdateRequest(5.0, "수정된 내용");

        Review existing = Review.create(
                3.0,
                "원래 내용",
                ownerUserNo,
                parkinglotId,
                reservationId
        );
        ReflectionTestUtils.setField(existing, "reviewId", reviewId);

        when(repository.findById(reviewId))
                .thenReturn(Optional.of(existing));

        CustomUser customUser = CustomUser.builder()
                .userNo(currentUserNo)
                .email("other@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.updateReview(request, reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_ACCESS_DENIED);
        verify(repository).findById(reviewId);
    }

    @Test
    @DisplayName("NotOk : 리뷰 수정 - 범위 밖의 별점")
    void updateReview_InvalidRating() {
        // given
        Long reviewId = 1L;
        Long userNo = 10L;
        Long parkinglotId = 100L;
        Long reservationId = 999L;

        // 잘못된 rating
        ReviewUpdateRequest request = buildUpdateRequest(0.0, "내용");

        // 리뷰는 존재한다고 가정
        Review existing = Review.create(
                3.0,
                "원래 내용",
                userNo,
                parkinglotId,
                reservationId
        );
        ReflectionTestUtils.setField(existing, "reviewId", reviewId);

        when(repository.findById(reviewId))
                .thenReturn(Optional.of(existing));

        // ✅ 권한 검증 통과하도록 현재 로그인 유저를 review의 userNo와 동일하게 세팅
        CustomUser customUser = CustomUser.builder()
                .userNo(userNo)
                .email("test@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.updateReview(request, reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_RATING);
        verify(repository).findById(reviewId);
    }



    @Test
    @DisplayName("NotOk : 리뷰 수정 - 대상 리뷰 없음")
    void updateReview_NotFound() {
        // given
        Long reviewId = 1L;
        ReviewUpdateRequest request = buildUpdateRequest(4.0, "내용");

        when(repository.findById(reviewId))
                .thenReturn(Optional.empty());

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.updateReview(request, reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_NOT_FOUND);
        verify(repository).findById(reviewId);
    }

    // ================== deleteReview ==================

    @Test
    @DisplayName("NotOk : 리뷰 삭제 - 삭제 실패")
    void deleteReview_DeleteFailed() {
        // given
        Long reviewId = 1L;
        Long userNo = 10L;
        Long parkinglotId = 100L;
        Long reservationId = 999L;

        Review existing = Review.create(
                4.0,
                "삭제할 리뷰",
                userNo,
                parkinglotId,
                reservationId
        );
        ReflectionTestUtils.setField(existing, "reviewId", reviewId);

        when(repository.findById(reviewId))
                .thenReturn(Optional.of(existing));

        CustomUser customUser = CustomUser.builder()
                .userNo(userNo)
                .email("test@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.deleteReview(reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_DELETE_FAILED);
        // ✅ 실제 구현에서 findById를 2번 호출하므로 2회 검증
        verify(repository, times(2)).findById(reviewId);
        // 실패 분기라 delete()는 호출 안 되거나, 호출 전에 예외 발생했을 가능성 있음
    }

    @Test
    @DisplayName("NotOk : 리뷰 삭제 - 권한 없음")
    void deleteReview_AccessDenied() {
        // given
        Long reviewId = 1L;
        Long ownerUserNo = 10L;
        Long currentUserNo = 99L;
        Long parkinglotId = 100L;
        Long reservationId = 999L;

        Review existing = Review.create(
                4.0,
                "삭제할 리뷰",
                ownerUserNo,
                parkinglotId,
                reservationId
        );
        ReflectionTestUtils.setField(existing, "reviewId", reviewId);

        when(repository.findById(reviewId))
                .thenReturn(Optional.of(existing));

        CustomUser customUser = CustomUser.builder()
                .userNo(currentUserNo)
                .email("other@example.com")
                .password("")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUser, null, customUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.deleteReview(reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_ACCESS_DENIED);
        verify(repository).findById(reviewId);
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("NotOk : 리뷰 삭제 - 대상 리뷰 없음")
    void deleteReview_NotFound() {
        // given
        Long reviewId = 1L;

        when(repository.findById(reviewId))
                .thenReturn(Optional.empty());

        // when
        BusinessException ex = assertThrows(BusinessException.class,
                () -> commandService.deleteReview(reviewId));

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REVIEW_NOT_FOUND);
        verify(repository).findById(reviewId);
        verify(repository, never()).delete(any());
    }


}