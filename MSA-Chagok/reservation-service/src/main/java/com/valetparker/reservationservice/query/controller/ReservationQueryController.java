package com.valetparker.reservationservice.query.controller;

import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.query.dto.response.ReservationListResponse;
import com.valetparker.reservationservice.query.dto.response.ReservationQueryResponse;
import com.valetparker.reservationservice.query.dto.response.ReviewReservationInfoResponse;
import com.valetparker.reservationservice.query.service.ReservationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationQueryController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping("/mypage/reservations/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationQueryResponse>> findByReservationId(
            @PathVariable Long reservationId
    ){
        ReservationQueryResponse response = reservationQueryService.getReservationDetailBy(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/mypage/reservation/search/{userNo}")
    public ResponseEntity<ApiResponse<ReservationListResponse>> findByUserId(
//            @AuthenticationPrincipal Long userNo //Security들어오면 사용
            @PathVariable Long userNo
    ) {
        ReservationListResponse response = reservationQueryService.getReservationsByUserNo(userNo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // review API: Review 생성용 reservation정보 전달
    @GetMapping("/reservation/{reservationId}")
    public ApiResponse<ReviewReservationInfoResponse> getInfoForReview(
            @PathVariable("reservationId") Long reservationId
    ) {
        Reservation reservation = reservationQueryService.getByReservationId(reservationId);
        ReviewReservationInfoResponse response = ReviewReservationInfoResponse.builder()
                .parkinglotId(reservation.getParkinglotId())
                .userNo(reservation.getUserNo())
                .reservationId(reservation.getReservationId())
                .build();
        return ApiResponse.success(response);
    }

    // payment API: Payment 생성용 reservation정보 전달
    @GetMapping("/payment/{reservationId}")
    public ApiResponse<PaymentResponse> getInfoForPaymentReservation(
            @PathVariable Long reservationId
    )   {
        PaymentResponse response = reservationQueryService.getInfoForPaymentReservation(reservationId);
        return ApiResponse.success(response);
    }
}
