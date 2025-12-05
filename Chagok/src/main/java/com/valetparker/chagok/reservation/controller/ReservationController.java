package com.valetparker.chagok.reservation.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

/*    @PostMapping("/regist")
    public ResponseEntity<ApiResponse> registerReservation(@RequestBody ReservationDto request) {
        ReservationDto saved = reservationService.regist(request);
        return ResponseEntity.ok(ApiResponse.success(saved));
    }*/
}
