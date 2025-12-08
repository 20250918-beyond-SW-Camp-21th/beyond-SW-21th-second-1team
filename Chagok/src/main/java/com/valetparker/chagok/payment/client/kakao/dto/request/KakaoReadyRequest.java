package com.valetparker.chagok.payment.client.kakao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoReadyRequest {
    // 클라이언트 요청

    @JsonProperty("partner_order_id")
    private String partnerOrderId; // 우리 시스템의 주문 번호 (예약 ID)

    @JsonProperty("partner_user_id")
    private String partnerUserId; // 사용자 ID

    private String itemName; // 상품명 (예: 2시간 주차권)
    private Integer quantity; // 상품 수량 (1)
    private Integer totalAmount; // 총 결제 금액
    private Integer taxFreeAmount; // 비과세 금액 (0)

    // YML 파일에서 설정한 리다이렉트 URL
    private String approval_url;
    private String cancel_url;
    private String fail_url;
}
