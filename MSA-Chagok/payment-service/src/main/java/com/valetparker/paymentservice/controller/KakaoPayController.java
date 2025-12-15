package com.valetparker.paymentservice.controller;

import com.valetparker.paymentservice.dto.request.KakaoPayReadyRequest;
import com.valetparker.paymentservice.dto.response.KakaoPayApproveResponse;
import com.valetparker.paymentservice.dto.response.KakaoPayReadyResponse;
import com.valetparker.paymentservice.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kakaopay")
@RequiredArgsConstructor
@Slf4j
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;

    // 단순 데모용 상태 저장 (실제 서비스에서는 DB 저장 필수)
    private String tempTid;
    private String tempPartnerOrderId;
    private String tempPartnerUserId;

    /**
     * 결제 준비 (Ready)
     */
    @PostMapping("/ready/{reservationId}")
    public ResponseEntity<KakaoPayReadyResponse> ready(
//            @RequestBody KakaoPayReadyRequest request,
            @PathVariable Long reservationId) {



        KakaoPayReadyResponse response = kakaoPayService.ready(reservationId);

        if (response != null) {
            this.tempTid = response.getTid();
            this.tempPartnerOrderId = String.format("ORDER%03d", reservationId);
            this.tempPartnerUserId = "USER" + reservationId;
            log.info("결제 준비 완료. TID: {}", response.getTid());
        }

        log.info("[READY CONTROLLER] tempTid={}, tempPartnerOrderId={}, tempPartnerUserId={}",
                tempTid, tempPartnerOrderId, tempPartnerUserId);

        return ResponseEntity.ok(response);
    }

    /**
     * 결제 성공 (Approve)
     */
    @GetMapping("/success")
    public ResponseEntity<KakaoPayApproveResponse> success(@RequestParam("pg_token") String pgToken) {

        log.info("결제 승인 요청. pg_token: {}", pgToken);

        // 저장해둔 정보로 승인 요청
        KakaoPayApproveResponse response = kakaoPayService.approve(
                pgToken,
                this.tempTid,
                this.tempPartnerOrderId,
                this.tempPartnerUserId);

        log.info("[SUCCESS CONTROLLER] tempTid={}, tempPartnerOrderId={}, tempPartnerUserId={}",
                tempTid, tempPartnerOrderId, tempPartnerUserId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제가 취소되었습니다.");
    }

    @GetMapping("/fail")
    public ResponseEntity<String> fail() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제에 실패했습니다.");
    }
}
