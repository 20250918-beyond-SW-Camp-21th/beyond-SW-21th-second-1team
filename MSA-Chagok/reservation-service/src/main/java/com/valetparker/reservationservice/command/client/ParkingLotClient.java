package com.valetparker.reservationservice.command.client;

import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.command.dto.response.ReservationParkingLotResponse;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "valetparker-parkinglot-service", configuration = FeignClientConfig.class)
public interface ParkingLotClient {

    // ParkinglotId 받아오기
    @GetMapping("/parkinglot/base")
    public ResponseEntity<ApiResponse<BaseInfoResponse>> getParkinglotBaseInfo(
            @RequestParam Long parkinglotId
    );
    // Parkinglot used ++

    // Parkinglot used --
}