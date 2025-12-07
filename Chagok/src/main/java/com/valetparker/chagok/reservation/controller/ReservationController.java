package com.valetparker.chagok.reservation.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.dto.request.ReservationCreateRequest;
import com.valetparker.chagok.reservation.dto.response.ReservationHistoryResponse;
import com.valetparker.chagok.reservation.dto.response.ReservationResponse;
import com.valetparker.chagok.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/regist")
    public ResponseEntity<ApiResponse<Long>> registerReservation(
            @RequestBody ReservationCreateRequest request
    ) {
        Long response = reservationService.createReservation(request);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<ReservationHistoryResponse>>> getReservationHistory(
            @RequestParam Long userNo
    ) {
        List<ReservationHistoryResponse> response = reservationService.getReservationHistory(userNo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }


}
