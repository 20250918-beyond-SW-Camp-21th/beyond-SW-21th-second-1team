package com.valetparker.paymentservice.service;

import com.valetparker.paymentservice.PaymentServiceApplication;
import com.valetparker.paymentservice.client.ReservationClient;
import com.valetparker.paymentservice.common.dto.ApiResponse;
import com.valetparker.paymentservice.common.dto.request.PaymentInfoRequest;
import com.valetparker.paymentservice.config.KakaoPayProperties;
import com.valetparker.paymentservice.dto.request.KakaoPayReadyRequest;
import com.valetparker.paymentservice.dto.response.KakaoPayApproveResponse;
import com.valetparker.paymentservice.dto.response.KakaoPayReadyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPayService {

    private final KakaoPayProperties kakaoPayProperties;
    private final RestTemplate restTemplate;
    private final ReservationClient reservationClient;

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String key = kakaoPayProperties.getSecretKey();

        // 디버깅용 로그 (나중에 제거)
        if (key == null) {
            log.error("CRITICAL: Secret Key is NULL! Check application-pay.yml");
        } else {
            log.info("Loaded Secret Key Length: {}", key.length());
            log.info("Loaded Secret Key (Masked): {}...", key.substring(0, Math.min(key.length(), 5)));
            key = key.trim(); // 공백 제거 안전장치
        }

        String auth = "SECRET_KEY " + key;
        headers.add("Authorization", auth);
        headers.add("Content-Type", "application/json");
        return headers;
    }

    /**
     * 카카오페이 결제 준비 요청
     */
    public KakaoPayReadyResponse ready(Long reservationId) {
        ApiResponse<PaymentInfoRequest> info = reservationClient.getPaymentInfo(reservationId);

        PaymentInfoRequest paymentInfo = info.getData();

        String partnerOrderId = String.format("ORDER_%03d", reservationId);
        String partnerUserId = "USER_" + reservationId;
        String itemName = "주차장 사용권";
        Integer quantity = 1;
        Integer totalAmount = paymentInfo.getTotalAmount();
        Integer taxFreeAmount = 0;


        log.info("[KAKAO READY] reservationId={}, partnerOrderId={}, partnerUserId={}",
                reservationId, partnerOrderId, partnerUserId);

        // 요청 헤더
        HttpHeaders headers = getHeaders();

        // 요청 바디
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", kakaoPayProperties.getCid());
        parameters.put("partner_order_id", partnerOrderId);
        parameters.put("partner_user_id", partnerUserId);
        parameters.put("item_name", itemName);
        parameters.put("quantity", quantity);
        parameters.put("total_amount", totalAmount);
        parameters.put("tax_free_amount", taxFreeAmount);

        // 디버깅: URL 확인
        log.info("Redirect URL Success: {}", kakaoPayProperties.getRedirectUrlSuccess());
        log.info("Redirect URL Cancel: {}", kakaoPayProperties.getRedirectUrlCancel());
        log.info("Redirect URL Fail: {}", kakaoPayProperties.getRedirectUrlFail());

        // 리다이렉트 URL (Properties에서 가져오기)
        parameters.put("approval_url", kakaoPayProperties.getRedirectUrlSuccess());
        parameters.put("cancel_url", kakaoPayProperties.getRedirectUrlCancel());
        parameters.put("fail_url", kakaoPayProperties.getRedirectUrlFail());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        // API 호출
        return restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoPayReadyResponse.class);
    }

    /**
     * 카카오페이 결제 승인 요청
     */
    public KakaoPayApproveResponse approve(String pgToken, String tid, String partnerOrderId, String partnerUserId) {

        log.info("[KAKAO APPROVE] tid={}, partnerOrderId={}, partnerUserId={}, pgToken={}",
                tid, partnerOrderId, partnerUserId, pgToken);

        // 요청 헤더
        HttpHeaders headers = getHeaders();

        // 요청 바디
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", kakaoPayProperties.getCid());
        parameters.put("tid", tid);
        parameters.put("partner_order_id", partnerOrderId);
        parameters.put("partner_user_id", partnerUserId);
        parameters.put("pg_token", pgToken);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, headers);

        // API 호출
        return restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoPayApproveResponse.class);
    }
}
