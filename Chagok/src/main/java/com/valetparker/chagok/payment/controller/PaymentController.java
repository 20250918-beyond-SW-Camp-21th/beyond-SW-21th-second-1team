package com.valetparker.chagok.payment.controller;

import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoReadyResponse;
import com.valetparker.chagok.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /** 1. 결제 준비 요청: 모바일 앱/웹에서 결제 시작 버튼 클릭 시 호출 */
    @PostMapping("/kakao/ready")
    public ResponseEntity<KakaoReadyResponse> readyToPay(
            @RequestParam Long reservationId,
            @RequestParam Long amount) {

        // 실제로는 reservationId를 통해 amount와 사용자 정보를 조회해야 합니다.
        KakaoReadyResponse response = paymentService.readyToPay(reservationId, amount);

        // 앱/웹에 결제창 URL을 반환하여 리다이렉트 유도
        return ResponseEntity.ok(response);
    }

    /** 2. 결제 성공 리다이렉트: 카카오페이 결제창에서 성공 시 호출 */
    @GetMapping("/kakao/success")
    public ResponseEntity<String> kakaoPaySuccess(
            @RequestParam("pg_token") String pgToken,
            @RequestParam("partner_order_id") String orderId) {

        try {
            // PG 토큰과 orderId를 사용하여 최종 승인 요청 및 DB 업데이트
            paymentService.confirmPayment(orderId, pgToken);

            // 앱/웹의 결제 성공 화면으로 리다이렉트하거나 성공 메시지 반환
            return ResponseEntity.ok("결제가 성공적으로 완료되었습니다.");

        } catch (Exception e) {
            // 실패 처리 (예: 로그 기록, 환불 로직, 실패 페이지 리다이렉트 등)
            return ResponseEntity.badRequest().body("결제 실패: " + e.getMessage());
        }
    }

    /** 3. 결제 취소 리다이렉트 */
    @GetMapping("/kakao/cancel")
    public ResponseEntity<String> kakaoPayCancel() {
        return ResponseEntity.ok("결제가 취소되었습니다.");
    }

    /** 4. 결제 실패 리다이렉트 */
    @GetMapping("/kakao/fail")
    public ResponseEntity<String> kakaoPayFail() {
        return ResponseEntity.status(400).body("결제에 실패하였습니다.");
    }
}
