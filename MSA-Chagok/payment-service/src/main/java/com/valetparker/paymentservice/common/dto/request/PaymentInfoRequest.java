package com.valetparker.paymentservice.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class PaymentInfoRequest {
    private Long reservationId;
    private Long parkinglotId;
    private Integer totalAmount;
    private String parkinglotName;
}
