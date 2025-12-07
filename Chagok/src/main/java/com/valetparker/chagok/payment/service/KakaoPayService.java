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
//
//    public KakaoPayReadyResponse kakaoPayReady(ParkinglotRequest request) {
//        Map<String, Object> parameters = new HashMap<>();
//
//        parameters.put("cid", payProperties.getCid());
//        parameters.put("partner_order_id", request.getParkinglotId());               // 주문 번호
//        parameters.put("partner_user_id", request.getName());                 // 사용자 ID
//        parameters.put("item_name", request.getItemName());                     // 상품명
//        parameters.put("quantity", request.getQuantity());                              // 수량, 숫자 문자열로 전달
//
//        /* basefee 1시간 or 30분? , unitfee = 추가금액 으로 진행하려면
//        * 주차장 엔티티에 수량, 상품이름 추가해서 사용하는게 좋을거 같습니다
//        * 예) 1시간권(기본), 30분(시간 추가)
//        *    2시간 이용시 1시간권 2개 or 1시간권 1개 + 30분권 2개
//        * */
//        parameters.put("total_amount", request.getBaseFee());         // 총 금액
//        parameters.put("vat_amount", "1000");                         // 부가세
//        parameters.put("tax_free_amount", "0");                       // 비과세
//        parameters.put("approval_url", "http://localhost:8080/api/v1/kakao-pay/approve");
//        parameters.put("cancel_url", "http://localhost:8080/api/v1/kakao-pay/cancel");
//        parameters.put("fail_url", "http://localhost:8080/kakao-pay/fail");
//
//        HttpEntity<Map<String, Object>> requestEntity =
//                new HttpEntity<>(parameters, this.getHeaders());
//
//        // 외부에 보낼 url
//        RestTemplate restTemplate = new RestTemplate();
//
//        kakaoReady = restTemplate.postForObject(
//                "https://open-api.kakao.com/v2/payment/ready",
//                requestEntity,
//                KakaoPayReadyResponse.class);
//
//        return kakaoReady;
//    }
}
