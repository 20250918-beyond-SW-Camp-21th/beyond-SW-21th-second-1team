package com.valetparker.chagok.payment.service;

import com.valetparker.chagok.payment.config.KakaoPayProperties;
import com.valetparker.chagok.payment.dto.KakaoPayReadyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaoPayService {

    private final KakaoPayProperties payProperties;
    private RestTemplate restTemplate = new RestTemplate();
    private KakaoPayReadyResponse kakaoReady;

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = "SECRET_KEY" + payProperties.getSecretKey();
        headers.add("Authorization", auth);
        headers.add("Content-Type", "application/json");
        return headers;
    }

    /*
    * 결제 완료 요청
    * */
    public KakaoPayReadyResponse kakaoPayReady() {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("cid", payProperties.getCid());
        parameters.put("partner_order_id", "ORDER_ID");     // 주문 번호
        parameters.put("partner_user_id", "USER_ID");       // 사용자 ID
        parameters.put("item_name", "ITEM_NAME");           // 상품명
        parameters.put("quantity", "1");                    // 수량, 숫자 문자열로 전달
        parameters.put("total_amount", "10000");            // 총 금액
        parameters.put("vat_amount", "1000");               // 부가세
        parameters.put("tax_free_amount", "0");             // 비과세
        parameters.put("approval_url", "http://localhost:8080/pay/success");
        parameters.put("cancel_url", "http://localhost:8080/pay/cancel");
        parameters.put("fail_url", "http://localhost:8080/pay/fail");

        HttpEntity<Map<String, Object>> requestEntity =
                new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakao.com/v2/payment/ready",
                requestEntity,
                KakaoPayReadyResponse.class);

        return kakaoReady;
    }
}
