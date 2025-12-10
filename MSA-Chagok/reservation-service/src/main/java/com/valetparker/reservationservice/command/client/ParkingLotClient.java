package com.valetparker.reservationservice.command.client;

import com.valetparker.reservationservice.command.dto.response.UsedSpotsUpdateResponse;
import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "valetparker-reparking-service", configuration = FeignClientConfig.class)
public interface ParkingLotClient {

    // ParkinglotId로 받아오기
    @GetMapping("/parkinglot/base")
    public ResponseEntity<ApiResponse<BaseInfoResponse>> getParkinglotBaseInfo(
            @RequestParam Long parkinglotId
    );

    // Parkinglot used ++
    @PutMapping("/parkinglot/using")
    public ResponseEntity<ApiResponse<BaseInfoResponse>> updateUsedSpots(@RequestBody UsedSpotsUpdateResponse request);
    // Parkinglot used --
}