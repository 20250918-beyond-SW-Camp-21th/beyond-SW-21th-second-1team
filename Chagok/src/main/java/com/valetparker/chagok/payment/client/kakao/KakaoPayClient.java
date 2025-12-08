package com.valetparker.chagok.payment.client.kakao;

import com.valetparker.chagok.payment.client.kakao.dto.request.KakaoReadyRequest;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoApproveResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoCancelResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoReadyResponse;
import com.valetparker.chagok.payment.config.KakaoPayProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class KakaoPayClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String cid;
    private final String secretKey;
    private final String baseUrl = "https://kapi.kakao.com/v1/payment";

    public KakaoPayClient(KakaoPayProperties properties) {
        this.cid = properties.getCid();
        this.secretKey = properties.getSecretKey();
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + secretKey);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8");
        return headers;
    }

    /* 1. 결제 준비 요청: /ready */
    public KakaoReadyResponse ready(KakaoReadyRequest request) {
        String url = baseUrl + "/ready";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", cid);
        params.add("partner_order_id", request.getPartnerOrderId());
        params.add("partner_user_id", request.getPartnerUserId());
        params.add("item_name", request.getItemName());
        params.add("quantity", String.valueOf(request.getQuantity()));
        params.add("total_amount", String.valueOf(request.getTotalAmount()));
        params.add("tax_free_amount", String.valueOf(request.getTaxFreeAmount()));
        params.add("approval_url", request.getApproval_url());
        params.add("cancel_url", request.getCancel_url());
        params.add("fail_url", request.getFail_url());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, createHeaders());

        return restTemplate.postForObject(url, entity, KakaoReadyResponse.class);
    }

    /* 2. 결제 승인 요청: /approve */
    public KakaoApproveResponse approve(String tid, String partnerOrderId, String pgToken) {
        String url = baseUrl + "/approve";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", cid);
        params.add("tid", tid);
        params.add("partner_order_id", partnerOrderId);
        params.add("partner_user_id", "parking_user");
        params.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, createHeaders());

        return restTemplate.postForObject(url, entity, KakaoApproveResponse.class);
    }

    /* 3. 결제 취소 요청: /cancel */
    public KakaoCancelResponse cancel(String tid, Long cancelAmount) {
        String url = baseUrl + "/cancel";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", cid);
        params.add("tid", tid);
        params.add("cancel_amount", String.valueOf(cancelAmount));
        params.add("cancel_tax_free_amount", "0");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, createHeaders());

        return Objects.requireNonNull(restTemplate.postForObject(url, entity, KakaoCancelResponse.class));
    }
}