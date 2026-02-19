package com.valetparker.paymentservice.common.dto;

import com.valetparker.paymentservice.domain.Payment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDto {

    private String partnerOrderId;  // 가맹점 주문 번호
    private String partnerUserId;   // 가맹점 회원 ID
    private String itemName;        // 상품명
    private Integer quantity;       // 수량
    private Integer totalAmount;    // 총 금액
    private Integer taxFreeAmount;  // 비과세 금액

    public static PaymentDto from(Payment payment) {
        return PaymentDto.builder()
                .partnerOrderId(payment.getPartnerOrderId())
                .partnerUserId(payment.getPartnerOrderId())
                .itemName(payment.getItemName())
                .quantity(payment.getQuantity())
                .totalAmount(payment.getAmount())
                .taxFreeAmount(0)
                .build();
    }
}
