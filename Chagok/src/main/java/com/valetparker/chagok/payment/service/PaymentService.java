package com.valetparker.chagok.payment.service;

import com.valetparker.chagok.payment.client.kakao.KakaoPayClient;
import com.valetparker.chagok.payment.client.kakao.dto.request.KakaoReadyRequest;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoCancelResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoReadyResponse;
import com.valetparker.chagok.payment.client.kakao.dto.response.KakaoApproveResponse;
import com.valetparker.chagok.payment.domain.Payment;
import com.valetparker.chagok.payment.enums.PaymentStatus;
import com.valetparker.chagok.payment.repository.PaymentRepository;
import com.valetparker.chagok.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KakaoPayClient kakaoPayClient;
//    private final ReservationService reservationService; // reservation 연결시 활성화

    @Value("${kakao.redirect-url.success}")
    private String successRedirectUrl;
    @Value("${kakao.redirect-url.cancel}")
    private String cancelRedirectUrl;
    @Value("${kakao.redirect-url.fail}")
    private String failRedirectUrl;

    /** 1. 결제 준비 요청 (Ready) - 결제창 URL 획득 */
    @Transactional
    public KakaoReadyResponse readyToPay(Long reservationId, Long amount) {

        // 1. 주문 번호 생성 및 Payment 객체 초기화 (INITIAL 상태)
        String partnerOrderId = "ORDER_" + reservationId + "_" + System.currentTimeMillis();

        Payment payment = Payment.builder()
                .reservationId(reservationId)
                .partnerOrderId(partnerOrderId)
                .amount(amount)
                .status(PaymentStatus.INITIAL)
                .build();

        payment = paymentRepository.save(payment);

        // 2. 카카오페이 Ready 요청 DTO 생성
        KakaoReadyRequest request = KakaoReadyRequest.builder()
                .partnerOrderId(partnerOrderId)
                .partnerUserId("parking_user_id_123")
                .itemName("주차권 구매")
                .quantity(1)
                .totalAmount(amount.intValue())
                .taxFreeAmount(0)
                .approval_url(successRedirectUrl)
                .cancel_url(cancelRedirectUrl)
                .fail_url(failRedirectUrl)
                .build();

        // 3. KakaoPayClient 호출 및 tid 획득
        KakaoReadyResponse response = kakaoPayClient.ready(request);

        // 4. tid 정보 DB 업데이트 (상태: PENDING_PAYMENT)
        payment.setReadyInfo(response.getTid());

        return response;
    }

    /** 2. 결제 승인 요청 (Approve) - 최종 결제 확정 */
    @Transactional
    public void confirmPayment(String partnerOrderId, String pgToken) {

        // 1. DB에서 tid 및 amount 조회 (유효성 검증)
        Payment payment = paymentRepository.findByPartnerOrderId(partnerOrderId)
                .orElseThrow(() -> new RuntimeException("결제 정보를 찾을 수 없습니다."));

        // 2. 카카오페이에 최종 승인 요청
        KakaoApproveResponse pgResponse = kakaoPayClient.approve(
                payment.getTid(),
                partnerOrderId,
                pgToken
        );

        // 3. 결제 트랜잭션 DB 상태 업데이트 및 최종 확정
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPgToken(pgToken);

        // 4.  예약 상태 최종 업데이트 및 주차 공간 할당
//        reservationService.completeReservationPayment(payment.getReservationId()); // reservation에서 가져올 값
    }

    /* 3. 결제 취소 요청 (Cancel) */
    @Transactional
    public void cancelPayment(String partnerOrderId) {

        Payment payment = paymentRepository.findByPartnerOrderId(partnerOrderId)
                .orElseThrow(() -> new RuntimeException("취소할 결제 정보를 찾을 수 없습니다."));

        if (payment.getStatus() == PaymentStatus.CANCELED) {
            throw new IllegalStateException("이미 취소된 결제입니다.");
        }

        // 2. 카카오페이에 취소 요청
        Long cancelAmount = payment.getAmount();
        KakaoCancelResponse pgResponse = kakaoPayClient.cancel(payment.getTid(), cancelAmount);

        // 3. 결제 트랜잭션 DB 상태 업데이트
        payment.setStatus(PaymentStatus.CANCELED);

        // 4. ⭐ 예약 상태 취소 및 주차 공간 회수 ⭐
//        reservationService.cancelReservation(payment.getReservationId());
    }
}
