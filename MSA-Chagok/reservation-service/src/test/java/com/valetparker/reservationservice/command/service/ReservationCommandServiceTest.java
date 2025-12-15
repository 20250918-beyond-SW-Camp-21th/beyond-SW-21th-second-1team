package com.valetparker.reservationservice.command.service;

import com.valetparker.reservationservice.command.client.ParkingLotClient;
import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationStartRequest;
import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.command.dto.response.UsedSpotsUpdateResponse;
import com.valetparker.reservationservice.command.repository.ReservationCommandRepository;
import com.valetparker.reservationservice.common.converter.LocalDateTimeConverter;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCommandServiceTests {

    @Mock
    private ReservationCommandRepository reservationCommandRepository;

    @Mock
    private ParkingLotClient parkingLotClient;

    @Mock
    private LocalDateTimeConverter localDateTimeConverter;

    @InjectMocks
    private ReservationCommandService reservationCommandService;

    public ReservationCreateRequest buildRequest() {
        // 실제 필드명에 맞춰서 작성 (parkingLotId + startTime)
        return ReservationCreateRequest.builder()
                .parkingLotId(1L)                   // parkingLotId
                .startTime("2025-12-12T10:00")      // startTime (문자열)
                .build();
    }


    /* ===========================
       createReservation() 관련
       =========================== */

    @Test
    @DisplayName("createReservation - 없는 주차장 선택 시 REGIST_ERROR_NO_PARKINGLOT 발생")
    void createReservation_fail() {
        // given
        ReservationCreateRequest request = buildRequest();
        Long userNo = 7L;

        // parkinglot-service 에서 body 가 null 로 오는 상황
        when(parkingLotClient.getParkinglotBaseInfo(request.getParkingLotId()))
                .thenReturn(ResponseEntity.ok(null));

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.createReservation(request, userNo)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REGIST_ERROR_NO_PARKINGLOT);
    }

    @Test
    @DisplayName("createReservation - 시작 시간이 파싱 불가한 경우 VALIDATION_ERROR_TIME_UNAVAILABLE 발생")
    void createReservation_invalidStartTime_throwsVALIDATION_ERROR_TIME_UNAVAILABLE() {
        // given
        ReservationCreateRequest request = buildRequest();
        Long userNo = 7L;

        BaseInfoResponse baseInfo = BaseInfoResponse.builder()
                .parkinglotId(request.getParkingLotId())
                .baseTime(60)
                .baseFee(2000)
                .name("샘플주차장")
                .build();

        ApiResponse<BaseInfoResponse> apiResponse = ApiResponse.success(baseInfo);

        when(parkingLotClient.getParkinglotBaseInfo(request.getParkingLotId()))
                .thenReturn(ResponseEntity.ok(apiResponse));

        // 날짜 파싱 실패 → converter 가 null 반환
        when(localDateTimeConverter.convert(request.getStartTime()))
                .thenReturn(null);

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.createReservation(request, userNo)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.VALIDATION_ERROR_TIME_UNAVAILABLE);
    }

    @Test
    @DisplayName("createReservation - 같은 주차장 동일 시간대에 예약 존재 시 REGIST_ERROR_TIME_CONFLICT 발생")
    void createReservation_timeConflict_throwsREGIST_ERROR_TIME_CONFLICT() {
        // given
        ReservationCreateRequest request = buildRequest();
        Long userNo = 7L;

        BaseInfoResponse baseInfo = BaseInfoResponse.builder()
                .parkinglotId(request.getParkingLotId())
                .baseTime(60)
                .baseFee(2000)
                .name("샘플주차장")
                .build();

        ApiResponse<BaseInfoResponse> apiResponse = ApiResponse.success(baseInfo);

        when(parkingLotClient.getParkinglotBaseInfo(request.getParkingLotId()))
                .thenReturn(ResponseEntity.ok(apiResponse));

        LocalDateTime start = LocalDateTime.of(2025, 12, 12, 10, 0);
        when(localDateTimeConverter.convert(request.getStartTime()))
                .thenReturn(start);

        // 이미 겹치는 예약 존재
        when(reservationCommandRepository
                .existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
                        eq(request.getParkingLotId()),
                        any(LocalDateTime.class),
                        any(LocalDateTime.class)
                ))
                .thenReturn(true);

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.createReservation(request, userNo)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REGIST_ERROR_TIME_CONFLICT);
    }

    /* ===========================
       reservationPayment() 관련
       =========================== */

    @Test
    @DisplayName("reservationPayment - 예약번호가 잘못된 경우 VALIDATION_ERROR_WRONG_RESERVATIONID 발생")
    void reservationPayment_wrongReservationId_throwsVALIDATION_ERROR_WRONG_RESERVATIONID() {
        // given
        Long reservationId = 10L;
        when(reservationCommandRepository.findByReservationIdAndIsCanceledFalse(reservationId))
                .thenReturn(null);   // 존재하지 않는 예약

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.reservationPayment(reservationId)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.VALIDATION_ERROR_WRONG_RESERVATIONID);
    }

    @Test
    @DisplayName("reservationPayment - 주차장 정보 조회 실패 시 REGIST_ERROR_NO_PARKINGLOT 발생")
    void reservationPayment_noParkinglot_throwsREGIST_ERROR_NO_PARKINGLOT() {
        // given
        Long reservationId = 10L;
        Long parkinglotId = 1L;

        Reservation reservation = mock(Reservation.class);
        when(reservation.getParkinglotId()).thenReturn(parkinglotId);

        when(reservationCommandRepository.findByReservationIdAndIsCanceledFalse(reservationId))
                .thenReturn(reservation);

        // parkinglot-service 응답 body 가 null
        when(parkingLotClient.getParkinglotBaseInfo(parkinglotId))
                .thenReturn(ResponseEntity.ok(null));

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.reservationPayment(reservationId)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.REGIST_ERROR_NO_PARKINGLOT);
    }

    /* ===========================
       startReservation() 관련
       =========================== */

    @Test
    @DisplayName("startReservation - 시간 파싱 실패 시 VALIDATION_ERROR_TIME_UNAVAILABLE 발생")
    void startReservation_invalidTime_throwsVALIDATION_ERROR_TIME_UNAVAILABLE() {
        // given
        ReservationStartRequest request = ReservationStartRequest.builder()
                .reservationId(10L)
                .parkinglotId(1L)
                .updateTime("invalidTime")
                .build();

        when(localDateTimeConverter.convert(request.getUpdateTime()))
                .thenReturn(null);   // 파싱 실패

        // when
        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> reservationCommandService.startReservation(request)
        );

        // then
        assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.VALIDATION_ERROR_TIME_UNAVAILABLE);
    }
}
