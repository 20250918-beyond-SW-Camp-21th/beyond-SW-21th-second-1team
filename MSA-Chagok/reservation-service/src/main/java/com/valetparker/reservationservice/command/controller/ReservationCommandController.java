package com.valetparker.reservationservice.command.controller;

import com.valetparker.reservationservice.command.dto.request.ReservationCommandRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.response.UsedSpotUpdateResponse;
import com.valetparker.reservationservice.command.dto.response.ReservationCommandResponse;
import com.valetparker.reservationservice.command.service.ReservationCommandService;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationCommandController {

    private final ReservationCommandService reservationCommandService;

    @PostMapping("/parkingLot/createReservation")
    public ResponseEntity<ApiResponse<ReservationCommandResponse>> createReservation(
            @RequestBody ReservationCreateRequest request, @RequestParam String userId
    ) {
        Long reservationId = reservationCommandService.createReservation(request, userId);
        ReservationCommandResponse reservationCommandResponse = ReservationCommandResponse.builder()
                .reservationId(reservationId)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationCommandResponse));
    }

}
