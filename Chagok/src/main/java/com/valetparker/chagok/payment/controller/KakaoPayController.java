package com.valetparker.chagok.payment.controller;

import com.valetparker.chagok.payment.client.kakao.KakaoPayProvider;
import com.valetparker.chagok.payment.client.kakao.dto.request.KakaoPayRequest.OrderRequest;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoPayResponse.ApproveResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoPayResponse.ReadyResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kakao-pay")
@Tag(name = "KakaoPay", description = "KakaoPay 기능 관련 API")
public class KakaoPayController {


//    private final KakaoPayProvider kakaoPayProvider;


//    // 카카오페이 결제를 준비하는 기능을 제공하는 api
//    @PostMapping("/ready")
//    public ReadyResponse ready(@RequestBody OrderRequest request) {
//        return kakaoPayProvider.ready(request);
//    }

//    // 카카오페이 결제 성공시 자동으로 호출되는 결제 승인 api
//    @GetMapping("/approve")
//    public ApproveResponse approve(@RequestParam("pg_token") String pgToken) {
//        return kakaoPayProvider.approve(pgToken);
//    }


}
