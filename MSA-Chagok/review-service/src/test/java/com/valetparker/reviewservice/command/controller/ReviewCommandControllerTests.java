package com.valetparker.reviewservice.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valetparker.reviewservice.command.dto.request.ReviewCreateRequest;
import com.valetparker.reviewservice.command.dto.request.ReviewUpdateRequest;
import com.valetparker.reviewservice.command.service.ReviewCommandService;
import com.valetparker.reviewservice.common.exception.BusinessException;
import com.valetparker.reviewservice.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReviewCommandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewCommandService reviewCommandService;

    @Test
    @DisplayName("POST /mypage/reservations/{reservationId}/review - 리뷰 등록 성공")
    void registerReview_Ok() throws Exception {
        Long reservationId = 1L;
        Long reviewId = 10L;

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .rating(4.0)
                .content("좋았어요")
                .build();

        Mockito.when(reviewCommandService.createReview(any(ReviewCreateRequest.class), eq(reservationId)))
                .thenReturn(reviewId);

        mockMvc.perform(post("/mypage/reservations/{reservationId}/review", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.reviewId").value(reviewId));
    }

    @Test
    @DisplayName("PUT /mypage/reviews/{reviewId} - 리뷰 수정 성공")
    void modifyReview_Ok() throws Exception {
        Long reviewId = 10L;

        ReviewUpdateRequest request = ReviewUpdateRequest.builder()
                .rating(5.0)
                .content("수정된 리뷰 내용")
                .build();

        Mockito.doNothing()
                .when(reviewCommandService)
                .updateReview(any(ReviewUpdateRequest.class), eq(reviewId));

        mockMvc.perform(put("/mypage/reviews/{reviewId}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("DELETE /mypage/reviews/{reviewId} - 리뷰 삭제 성공")
    void deleteReview_Ok() throws Exception {
        Long reviewId = 10L;

        Mockito.doNothing()
                .when(reviewCommandService)
                .deleteReview(reviewId);

        mockMvc.perform(delete("/mypage/reviews/{reviewId}", reviewId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("POST /mypage/reservations/{reservationId}/review - 잘못된 별점으로 인한 검증 오류")
    void registerReview_InvalidRating() throws Exception {
        Long reservationId = 1L;

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .rating(0.0)
                .content("별점 잘못된 요청")
                .build();

        Mockito.when(reviewCommandService.createReview(any(ReviewCreateRequest.class), eq(reservationId)))
                .thenThrow(new BusinessException(ErrorCode.INVALID_RATING));

        mockMvc.perform(post("/mypage/reservations/{reservationId}/review", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mypage/reservations/{reservationId}/review - 권한 없음")
    void registerReview_AccessDenied() throws Exception {
        Long reservationId = 1L;

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .rating(4.0)
                .content("권한 없는 유저 요청")
                .build();

        Mockito.when(reviewCommandService.createReview(any(ReviewCreateRequest.class), eq(reservationId)))
                .thenThrow(new BusinessException(ErrorCode.REVIEW_ACCESS_DENIED));

        mockMvc.perform(post("/mypage/reservations/{reservationId}/review", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mypage/reservations/{reservationId}/review - 리뷰 저장 실패")
    void registerReview_CreateFailed() throws Exception {
        Long reservationId = 1L;

        ReviewCreateRequest request = ReviewCreateRequest.builder()
                .rating(4.0)
                .content("저장 실패 테스트")
                .build();

        Mockito.when(reviewCommandService.createReview(any(ReviewCreateRequest.class), eq(reservationId)))
                .thenThrow(new BusinessException(ErrorCode.REVIEW_CREATE_FAILED));

        mockMvc.perform(post("/mypage/reservations/{reservationId}/review", reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}