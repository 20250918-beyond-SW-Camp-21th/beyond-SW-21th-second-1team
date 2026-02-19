package com.valetparker.reservationservice.command.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationEndRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationStartRequest;
import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.command.dto.response.ReservationCommandResponse;
import com.valetparker.reservationservice.command.dto.response.UsedSpotsUpdateResponse;
import com.valetparker.reservationservice.command.service.ReservationCommandService;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import com.valetparker.reservationservice.common.exception.GlobalExceptionHandler;
import com.valetparker.reservationservice.common.security.CustomUserDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationCommandController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class ReservationCommandControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean
    private ReservationCommandService reservationCommandService;

    /* -------------------------------
       테스트용 Authentication 주입
       ------------------------------- */
    private void setCustomUserPrincipal(Long userNo) {
        CustomUserDetails principal = Mockito.mock(CustomUserDetails.class);
        Mockito.when(principal.getUserNo()).thenReturn(userNo);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal, null, null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void setUserDetailsPrincipal(UserDetails principal) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    /* =========================================================
       1) 예약 생성 성공 테스트
       ========================================================= */
    @Test
    @DisplayName("예약 생성 성공 테스트")
    void reservationCreate_success() throws Exception {
        setCustomUserPrincipal(7L);

        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .parkingLotId(10L)
                .startTime("2025-12-12T10:00")
                .build();

        Mockito.when(reservationCommandService.createReservation(any(ReservationCreateRequest.class), eq(7L)))
                .thenReturn(100L);

        mockMvc.perform(post("/reservation/createReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.reservationId").value(100L));
    }

    /* =========================================================
       2) 예약 생성 실패 테스트 (reservationId가 생성되지 않았습니다.)
       - 컨트롤러는 service 예외를 받아서 GlobalExceptionHandler가 처리
       ========================================================= */
    @Test
    @DisplayName("예약 생성 실패 테스트 - reservationId가 생성되지 않았습니다.")
    void reservationCreate_failed_reservationIdNotCreated() throws Exception {
        setCustomUserPrincipal(7L);

        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .parkingLotId(10L)
                .startTime("2025-12-12T10:00")
                .build();

        Mockito.when(reservationCommandService.createReservation(any(ReservationCreateRequest.class), eq(7L)))
                .thenThrow(new BusinessException(ErrorCode.REGIST_ERROR));

        mockMvc.perform(post("/reservation/createReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.REGIST_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.REGIST_ERROR.getMessage()));
    }

    /* =========================================================
       3) 결제 호출 성공 테스트
       ========================================================= */
    @Test
    @DisplayName("결제 호출 성공 테스트")
    void paymentCall_success() throws Exception {
        Long reservationId = 10L;

        PaymentResponse paymentResponse = PaymentResponse.builder()
                .reservationId(reservationId)
                .parkinglotId(1L)
                .totalAmount(3000)
                .parkinglotName("강남 주차장")
                .build();

        Mockito.when(reservationCommandService.reservationPayment(eq(reservationId)))
                .thenReturn(paymentResponse);

        mockMvc.perform(post("/payment/{reservationId}", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.reservationId").value(10L))
                .andExpect(jsonPath("$.data.totalAmount").value(3000))
                .andExpect(jsonPath("$.data.parkinglotName").value("강남 주차장"));
    }

    /* =========================================================
       4) 결제 호출 실패 테스트 (response 가 생성되지 않았습니다.)
       ========================================================= */
    @Test
    @DisplayName("결제 호출 실패 테스트 - response가 생성되지 않았습니다.")
    void paymentCall_failed_responseNotCreated() throws Exception {
        Long reservationId = 10L;

        // 보통 이 경우 INTERNAL_SERVER_ERROR 또는 NOT_FOUND 중 하나로 던짐
        // 네 요구사항 문구가 “response 생성 실패”라서 INTERNAL_SERVER_ERROR로 맞춤
        Mockito.when(reservationCommandService.reservationPayment(eq(reservationId)))
                .thenThrow(new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR));

        mockMvc.perform(post("/payment/{reservationId}", reservationId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.INTERNAL_SERVER_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

    /* =========================================================
       5) 예약 이용시작 성공 테스트
       ========================================================= */
    @Test
    @DisplayName("예약 이용 시작 성공 테스트")
    void startReservation_success() throws Exception {
        ReservationStartRequest request = ReservationStartRequest.builder()
                .reservationId(10L)
                .parkinglotId(1L)
                .updateTime("2025-12-12T10:30")
                .build();

        UsedSpotsUpdateResponse response = UsedSpotsUpdateResponse.builder()
                .parkinglotId(1L)
                .using(true)
                .build();

        Mockito.when(reservationCommandService.startReservation(any(ReservationStartRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/reservation/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.using").value(true));
    }

    /* =========================================================
       6) 예약 이용 종료 성공 테스트
       ========================================================= */
    @Test
    @DisplayName("예약 이용 종료 성공 테스트")
    void quitReservation_success() throws Exception {
        // quitReservation()은 @AuthenticationPrincipal UserDetails user 를 받지만,
        // 이 메소드에서는 user 변수를 사용하지 않음(코드상).
        // 그래도 null 안전하게 principal 넣어줄 수 있음.
        UserDetails userPrincipal = Mockito.mock(UserDetails.class);
        Mockito.when(userPrincipal.getUsername()).thenReturn("tester");
        setUserDetailsPrincipal(userPrincipal);

        ReservationEndRequest request = ReservationEndRequest.builder()
                .reservationId(10L)
                .parkinglotId(1L)
                .build();

        UsedSpotsUpdateResponse response = UsedSpotsUpdateResponse.builder()
                .parkinglotId(1L)
                .using(false)
                .build();

        Mockito.when(reservationCommandService.finishReservation(any(ReservationEndRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/reservation/quit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.using").value(false));
    }

    /* =========================================================
       7) 예약 이용 시작 실패 테스트 (startReservation() 메소드 failure로 인해 실패)
       ========================================================= */
    @Test
    @DisplayName("예약 이용 시작 실패 테스트 - startReservation failure")
    void startReservation_failed() throws Exception {
        ReservationStartRequest request = ReservationStartRequest.builder()
                .reservationId(10L)
                .parkinglotId(1L)
                .updateTime("2025-12-12T09:00")
                .build();

        // “시작 실패”를 대표하는 에러코드로 EARLY_START 사용(너 ErrorCode 목록에 있음)
        Mockito.when(reservationCommandService.startReservation(any(ReservationStartRequest.class)))
                .thenThrow(new BusinessException(ErrorCode.VALIDATION_ERROR_EARLY_START));

        mockMvc.perform(put("/reservation/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR_EARLY_START.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR_EARLY_START.getMessage()));
    }

    /* =========================================================
       8) 예약 이용 종료 실패 테스트
       ========================================================= */
    @Test
    @DisplayName("예약 이용 종료 실패 테스트")
    void quitReservation_failed() throws Exception {
        UserDetails userPrincipal = Mockito.mock(UserDetails.class);
        Mockito.when(userPrincipal.getUsername()).thenReturn("tester");
        setUserDetailsPrincipal(userPrincipal);

        ReservationEndRequest request = ReservationEndRequest.builder()
                .reservationId(9999L)
                .parkinglotId(1L)
                .build();

        // 종료 실패는 보통 조회 실패/유효하지 않은 예약번호 중 하나로 던짐
        Mockito.when(reservationCommandService.finishReservation(any(ReservationEndRequest.class)))
                .thenThrow(new BusinessException(ErrorCode.VALIDATION_ERROR_WRONG_RESERVATIONID));

        mockMvc.perform(put("/reservation/quit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR_WRONG_RESERVATIONID.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.VALIDATION_ERROR_WRONG_RESERVATIONID.getMessage()));
    }
}
