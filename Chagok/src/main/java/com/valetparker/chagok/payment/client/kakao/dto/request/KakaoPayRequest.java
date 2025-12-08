package com.valetparker.chagok.payment.client.kakao.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class KakaoPayRequest {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    // 클라이언트로부터 주문 요청에 대한 정보를 받는 DTO
    public static class OrderRequest {
        /**
         * 필수 파라미터 (Required = O)
         * 파라미터명 변경 불가 - 카카오페이 API 고정 필수파라미터
         */

        // 가맹점 ID (CID)
        @JsonProperty("TC0ONETIME")
        private String cid; // 예: "TC0ONETIME" (테스트 가맹점 ID)

        // 가맹점 주문 번호 (partner_order_id)
        @JsonProperty("partner_order_id")
        private String partnerOrderId; // 예: "ORD202512020001"

        // 가맹점 회원 ID (partner_user_id)
        @JsonProperty("partner_user_id")
        private String partnerUserId; // 예: "USER_001"

        // 상품명 (item_name)
        @JsonProperty("item_name")
        private String itemName; // 예: "ValetParker n시간"

        // 상품 수량 (quantity)
        @JsonProperty("quantity")
        private Integer quantity; // 예: 1

        // 전체 결제 금액 (total_amount)
        @JsonProperty("total_amount")
        private Integer totalAmount; // 예: 55000

        // 비과세 금액 (tax_free_amount) - 선택이지만, 정확한 금액 계산을 위해 포함
        @JsonProperty("tax_free_amount")
        private Integer taxFreeAmount; // 예: 0 (전액 과세 시)

        // 결제 성공 시 리다이렉트 URL (approval_url)
        @JsonProperty("approval_url")
        private String approvalUrl; // 예: "http://localhost:8080/pay/success"

        // 결제 취소 시 리다이렉트 URL (cancel_url)
        @JsonProperty("cancel_url")
        private String cancelUrl; // 예: "http://localhost:8080/pay/cancel"

        // 결제 실패 시 리다이렉트 URL (fail_url)
        @JsonProperty("fail_url")
        private String failUrl; // 예: "http://localhost:8080/pay/fail"

    }
}