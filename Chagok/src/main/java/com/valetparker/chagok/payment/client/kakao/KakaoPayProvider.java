package com.valetparker.chagok.payment.client.kakao;


import com.valetparker.chagok.payment.client.kakao.dto.request.KakaoPayRequest;
import com.valetparker.chagok.payment.client.kakao.dto.request.KakaoPayRequest.OrderRequest;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoPayResponse.ApproveResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoPayResponse.ReadyResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KakaoPayProvider {

    @Value("${kakaopay.secretKey}")
    private String secretKey;

    @Value("${kakaopay.cid}")
    private String cid;

//    public ReadyResponse ready(OrderRequest request) {
//
//        KakaoPayRequest payProperties = new KakaoPayRequest();
//
//        Map<String, String> parameters = new HashMap<>();
//
//        parameters.put("cid", payProperties.getCid());
//        parameters.put("partner_order_id", request.getParkinglotId());          // 주문 번호 (reservation)
//        parameters.put("partner_user_id", request.getName());                   // 사용자 ID (Parkinglot)
//        parameters.put("item_name", request.getItemName());                     // 상품명 (Parkinglot
//        parameters.put("quantity", request.getQuantity());                      // 수량, 숫자 문자열로 전달 (Parkinglot)
//
//        /* basefee 1시간 or 30분? , unitfee = 추가금액 으로 진행하려면
//         * 주차장 엔티티에 수량, 상품이름 추가해서 사용하는게 좋을거 같습니다
//         * 예) 1시간권(기본), 30분(시간 추가)
//         *    2시간 이용시 1시간권 2개 or 1시간권 1개 + 30분권 2개
//         * */
//        parameters.put("total_amount", request.getBaseFee());         // 총 금액 (Parkinglot)
//        parameters.put("vat_amount", "1000");                         // 부가세
//        parameters.put("tax_free_amount", "0");                       // 비과세
//        parameters.put("approval_url", "http://localhost:8080/api/v1/kakao-pay/approve");
//        parameters.put("cancel_url", "http://localhost:8080/api/v1/kakao-pay/cancel");
//        parameters.put("fail_url", "http://localhost:8080/kakao-pay/fail");
//
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(parameters, getHeaders());
//
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://open-api.kakaopay.com/online/v1/payment/ready";
//        ResponseEntity<ReadyResponse> response = restTemplate.postForEntity(url, entity, ReadyResponse.class);
//
//        SessionProvider.addAttribute("tid",
//                Objects.requireNonNull(response.getBody()).getTid());
//
//        return response.getBody();
//    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "SECRET_KEY " + secretKey);
        headers.add("Content-type", "application/json");
        return headers;
    }

    public ApproveResponse approve(String pgToken) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", cid);
        parameters.put("tid", SessionProvider.getStringAttribute("tid"));
        parameters.put("partner_order_id", "1234567890");
        parameters.put("partner_user_id", "1234567890");
        parameters.put("pg_token", pgToken); // 결제승인 요청을 인증하는 토큰

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(parameters, getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://open-api.kakaopay.com/online/v1/payment/approve";
        ResponseEntity<ApproveResponse> response = restTemplate.postForEntity(url, entity, ApproveResponse.class);

        return response.getBody();
    }
}
