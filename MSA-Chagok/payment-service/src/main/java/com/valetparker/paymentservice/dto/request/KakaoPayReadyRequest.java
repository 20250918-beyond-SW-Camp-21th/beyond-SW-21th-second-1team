package com.valetparker.paymentservice.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayReadyRequest {

    // 결제 준비 요청 시 클라이언트로부터 받을 데이터

    private String partnerOrderId;  // 가맹점 주문 번호
    private String partnerUserId;   // 가맹점 회원 ID
    private String itemName;        // 상품명
    private Integer quantity;       // 수량
    private Integer totalAmount;    // 총 금액
    private Integer taxFreeAmount;  // 비과세 금액

    // 추가적으로 필요한 필드가 있다면 확장 가능
}
