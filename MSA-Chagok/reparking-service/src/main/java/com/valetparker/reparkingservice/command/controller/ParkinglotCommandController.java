package com.valetparker.reparkingservice.command.controller;

import com.valetparker.reparkingservice.command.dto.ParkinglotCommandResponse;
import com.valetparker.reparkingservice.command.dto.ParkinglotCreateRequest;
import com.valetparker.reparkingservice.command.dto.UsedSpotsUpdateRequest;
import com.valetparker.reparkingservice.command.service.ParkinglotCommandService;
import com.valetparker.reparkingservice.common.dto.ApiResponse;
import com.valetparker.reparkingservice.common.entity.Parkinglot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequiredArgsConstructor
public class ParkinglotCommandController {

    private final ParkinglotCommandService parkinglotCommandService;

    @PostMapping("/parkinglot/register")
    public ResponseEntity<ApiResponse<ParkinglotCommandResponse>> registerParkinglot(
            @RequestBody ParkinglotCreateRequest request
    ) {
        Long parkinglotId = parkinglotCommandService.createParkinglot(request);
        ParkinglotCommandResponse response = ParkinglotCommandResponse.builder()
                .parkinglotId(parkinglotId)
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    @PutMapping("/parkinglot/using")
    public ResponseEntity<ApiResponse<Void>> updateUsedSpots(
            @RequestBody UsedSpotsUpdateRequest request
    ) {
        parkinglotCommandService.updateUsedSpots(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
