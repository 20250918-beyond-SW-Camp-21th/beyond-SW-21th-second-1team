package com.valetparker.reservationservice.command.controller;

import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationEndRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationStartRequest;
import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.command.dto.response.UsedSpotsUpdateResponse;
import com.valetparker.reservationservice.command.dto.response.ReservationCommandResponse;
import com.valetparker.reservationservice.command.service.ReservationCommandService;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationCommandController {

    private final ReservationCommandService reservationCommandService;

    // 예약 생성
    @PostMapping("/reservation/createReservation/{userNo}")
    public ResponseEntity<ApiResponse<ReservationCommandResponse>> createReservation(
            @RequestBody ReservationCreateRequest request, @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long reservationId = reservationCommandService.createReservation(request, userDetails.getUserNo());
        ReservationCommandResponse reservationCommandResponse = ReservationCommandResponse.builder()
                .reservationId(reservationId)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationCommandResponse));
    }

    // 결제 호출
    @PostMapping("/payment/{reservationId}")
    public ResponseEntity<ApiResponse<PaymentResponse>> createPayment(
            @PathVariable Long reservationId
    )   {
        PaymentResponse response = reservationCommandService.createPayment(reservationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    // 예약 이용 시작
    @PutMapping("/reservation/start")
    public ResponseEntity<ApiResponse<UsedSpotsUpdateResponse>> startReservation(
            @RequestBody ReservationStartRequest request
    )   {
        UsedSpotsUpdateResponse response = reservationCommandService.startReservation(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

    // 예약 이용 종료
    @PutMapping("/reservation/quit")
    public ResponseEntity<ApiResponse<UsedSpotsUpdateResponse>> quitReservation(
            @RequestBody ReservationEndRequest request, @AuthenticationPrincipal UserDetails user
    )   {
        UsedSpotsUpdateResponse response = reservationCommandService.finishReservation(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(response));
    }

}
