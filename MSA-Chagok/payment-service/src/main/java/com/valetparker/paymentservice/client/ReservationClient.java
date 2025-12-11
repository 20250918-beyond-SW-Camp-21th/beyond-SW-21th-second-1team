package com.valetparker.paymentservice.client;

import com.valetparker.paymentservice.common.dto.ApiResponse;
import com.valetparker.paymentservice.common.dto.request.PaymentInfoRequest;
import com.valetparker.paymentservice.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "valetparker-reservation-service", configuration = FeignClientConfig.class)
public interface ReservationClient {

    @GetMapping("/payment/{reservationId}")
    public ApiResponse<PaymentInfoRequest> getPaymentInfo(@PathVariable("reservationId") Long reservationId);
}
