package com.valetparker.paymentservice.client;

import com.valetparker.paymentservice.common.dto.ApiResponse;
import com.valetparker.paymentservice.common.dto.request.PaymentInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "valetparker-reservation-service")
public interface ReservationClient {

    @GetMapping("/payment/{reservationId}")
    public ApiResponse<PaymentInfoRequest> getPaymentInfo(Long reservationId);
}
