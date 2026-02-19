package com.valetparker.chagok.payment.client.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayReadyResponse {

    private String tid; // 결제 고유 번호

    @JsonProperty("next_redirect_app_url")
    private String nextRedirectAppUrl;

    @JsonProperty("next_redirect_mobile_url")
    private String nextRedirectMobileUrl;

    @JsonProperty("next_redirect_pc_url")
    private String nextRedirectPcUrl;

    @JsonProperty("android_app_scheme")
    private String androidAppScheme;

    @JsonProperty("ios_app_scheme")
    private String iosAppScheme;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
