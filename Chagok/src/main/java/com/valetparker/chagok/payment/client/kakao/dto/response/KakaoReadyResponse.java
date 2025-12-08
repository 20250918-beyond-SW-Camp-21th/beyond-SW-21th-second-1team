package com.valetparker.chagok.payment.client.kakao.dto.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoReadyResponse {
    private String tid;                  // 결제 고유 번호 (다음 승인 단계에 사용)
    private String next_redirect_pc_url; // PC 웹 결제 페이지 URL
    private String created_at;
}
