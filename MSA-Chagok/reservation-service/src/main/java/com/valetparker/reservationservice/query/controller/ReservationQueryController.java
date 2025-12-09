package com.valetparker.reservationservice.query.controller;

import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.dto.ReservationDto;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.query.dto.response.ReservationDetailResponse;
import com.valetparker.reservationservice.query.service.ReservationQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationQueryController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping("/mypage/reservations/{reservationId}")
    public ResponseEntity<ApiResponse<ReservationDetailResponse>> findByReservationId(
            @PathVariable Long reservationId
    ){
        ReservationDetailResponse response = reservationQueryService.getReservationDetailBy(reservationId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
