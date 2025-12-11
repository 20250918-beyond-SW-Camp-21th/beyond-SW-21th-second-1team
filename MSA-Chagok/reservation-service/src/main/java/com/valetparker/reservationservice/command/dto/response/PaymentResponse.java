package com.valetparker.reservationservice.command.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {
    private Long reservationId;
    private Long parkinglotId;
    private Integer totalAmount;
    private String parkinglotName;
}
