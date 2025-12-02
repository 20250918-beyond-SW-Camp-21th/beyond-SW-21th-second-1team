package com.valetparker.chagok.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPayReadyResponse {

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
