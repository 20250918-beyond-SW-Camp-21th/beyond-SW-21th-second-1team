package com.valetparker.chagok.payment.domain;

import com.valetparker.chagok.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payment")
@Getter
public class PaymentTransaction {

    /* 1. 기본 키 (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // ----------------------------------------------------------------------

    /* 2. 카카오페이/가맹점 식별자 (취소/조회 API 호출에 사용) */

    // 카카오페이 거래 고유 번호 (Transaction ID) - 취소 시 필수
    @Column(name = "transaction_id", nullable = false, unique = true, length = 64)
    private String transactionId;

    // 카카오페이 승인 번호 (Approval ID)
    @Column(name = "approval_id", length = 64)
    private String approvalId;

    // 가맹점 주문 번호 (Order와의 연결고리)
    @Column(name = "partner_order_id", nullable = false, length = 100)
    private String partnerOrderId;

    // 가맹점 회원 ID
    @Column(name = "partner_user_id", nullable = false, length = 100)
    private String partnerUserId;

    // ----------------------------------------------------------------------

    /* 3. 금액 및 상태 정보 (환불 로직 관리) */

    // 최초 결제 금액 (전체 취소 금액의 기준)
    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    // 취소 금액
    @Column(name = "canceled_amount", nullable = false)
    private Integer canceledAmount = 0;

    // 결제 상태 (ENUM 사용)
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    // ----------------------------------------------------------------------

    /* 4. 시간 정보 */

    // 결제 승인 시간
    @Column(name = "pay_created_at", nullable = false)
    private LocalDateTime payCreatedAt;

    // 결제 취소 시간
    @Column(name = "refund_created_at")
    private LocalDateTime refundCreatedAt;

    // JPA 사용을 위한 기본 생성자
    public PaymentTransaction() {
    }
}