package com.valetparker.chagok.reservation.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.reservation.dto.request.ReservationCreateRequest;
import com.valetparker.chagok.reservation.dto.response.ReservationHistoryResponse;
import com.valetparker.chagok.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
//uid pid
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

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> cancelReservation(
            @RequestParam Long reservationId
    ) {
      Long isCanceled = reservationService.cancelReservation(reservationId);
      return ResponseEntity.ok(ApiResponse.success(isCanceled));
    }
}
