package com.valetparker.reservationservice.command.controller;

import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationUpdateRequest;
import com.valetparker.reservationservice.command.dto.response.ReservationCommandResponse;
import com.valetparker.reservationservice.command.service.ReservationCommandService;
import com.valetparker.reservationservice.common.dto.ApiResponse;
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

    @PostMapping("/reservation/createReservation/{userNo}")
    public ResponseEntity<ApiResponse<ReservationCommandResponse>> createReservation(
            @RequestBody ReservationCreateRequest request, @PathVariable Long userNo
    ) {
        Long reservationId = reservationCommandService.createReservation(request, userNo);
        ReservationCommandResponse reservationCommandResponse = ReservationCommandResponse.builder()
                .reservationId(reservationId)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(reservationCommandResponse));
    }

/*    // reservation 상태 변경
    @PostMapping("/reservation/updateReservation")
    public ResponseEntity<ApiResponse<ReservationCommandResponse>> updateReservation(
            @RequestBody ReservationUpdateRequest request, @RequestParam String userId
    ) {
        Long reservationId = reservationCommandService.updateReservation(request, userId);
    }*/

}
