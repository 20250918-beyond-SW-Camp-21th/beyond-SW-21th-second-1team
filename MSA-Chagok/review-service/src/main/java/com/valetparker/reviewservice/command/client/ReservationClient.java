package com.valetparker.reviewservice.command.client;

import com.valetparker.reviewservice.command.dto.response.ReviewReservationInfoResponse;
import com.valetparker.reviewservice.common.dto.ApiResponse;
import com.valetparker.reviewservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "valetparker-reservation-service", configuration = FeignClientConfig.class)
public interface ReservationClient {

    @GetMapping("/reservation/{reservationId}")
    ApiResponse<ReviewReservationInfoResponse> getReservation(
            @PathVariable("reservationId") Long reservationId);
}