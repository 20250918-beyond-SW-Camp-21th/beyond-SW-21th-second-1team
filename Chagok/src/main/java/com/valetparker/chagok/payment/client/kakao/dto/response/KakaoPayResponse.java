package com.valetparker.chagok.payment.client.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


public class KakaoPayResponse {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ReadyResponse {
        // 거래 고유 번호 (tid) - 3단계 승인 요청 시 필수
        @JsonProperty("tid")
        private String tid;

        // 모바일 웹/앱용 결제 페이지 URL
        @JsonProperty("next_redirect_mobile_url")
        private String nextRedirectMobileUrl;

        // PC 웹용 결제 페이지 URL
        @JsonProperty("next_redirect_pc_url")
        private String nextRedirectPcUrl;

        // QR코드 요청 시 URL
        @JsonProperty("next_redirect_app_url")
        private String nextRedirectAppUrl;

        // 결제 준비 요청 시간 (ISO 8601 형식)
        @JsonProperty("created_at")
        private String createdAt;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)

    // 결제 승인 완료 처리 후 카카오페이 서버로부터 전달받는 값에 대한 DTO
    public static class ApproveResponse {
        String aid;                 // 요청 고유 번호
        String tid;                 // 결제 고유 번호
        String cid;                 // 가맹점 코드
        String partner_order_id;    // 가맹점 주문번호
        String partner_user_id;     // 가맹점 회원 id
        String payment_method_type; // 결제 수단, CARD 또는 MONEY 중 하나
        String item_name;           // 상품 이름
        String item_code;           // 상품 코드
        int quantity;               // 상품 수량
        String created_at;          // 결제 준비 요청 시각
        String approved_at;         // 결제 승인 시각
        String payload;             // 결제 승인 요청에 대해 저장한 값, 요청 시 전달된 내용
    }
}
