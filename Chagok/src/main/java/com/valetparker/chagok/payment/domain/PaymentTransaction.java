package com.valetparker.chagok.payment.domain;

import com.valetparker.chagok.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_payment") // DB 테이블명은 소문자 스네이크 케이스를 사용합니다.
public class PaymentTransaction {

    /**
     * 1. 기본 키 (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    // ----------------------------------------------------------------------

    /**
     * 2. 카카오페이/가맹점 식별자 (취소/조회 API 호출에 사용)
     */

    // 카카오페이 거래 고유 번호 (Transaction ID) - 취소 시 필수
    @Column(name = "tid", nullable = false, unique = true, length = 64)
    private String tid;

    // 카카오페이 승인 번호 (Approval ID)
    @Column(name = "aid", length = 64)
    private String aid;

    // 가맹점 주문 번호 (Order와의 연결고리)
    @Column(name = "partner_order_id", nullable = false, length = 100)
    private String partnerOrderId;

    // 가맹점 회원 ID
    @Column(name = "partner_user_id", nullable = false, length = 100)
    private String partnerUserId;

    // ----------------------------------------------------------------------

    /**
     * 3. 금액 및 상태 정보 (환불 로직 관리)
     */

    // 최초 결제 금액 (전체 취소 금액의 기준)
    @Column(name = "total_amount", nullable = false)
    private int totalAmount;

    // 현재까지 취소된 금액
    @Column(name = "canceled_amount", nullable = false)
    private int canceledAmount = 0;

    // 결제 상태 (ENUM 사용)
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    // ----------------------------------------------------------------------

    /**
     * 4. 시간 정보
     */

    // 결제 승인 시간
    @Column(name = "pay_created_at", nullable = false)
    private LocalDateTime payCreatedAt;

    // JPA 사용을 위한 기본 생성자
    public PaymentTransaction() {}

    // Getter
    public Long getPaymentId() {
        return paymentId;
    }

    public String getTid() {
        return tid;
    }

    public String getAid() {
        return aid;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getCanceledAmount() {
        return canceledAmount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getPayCreatedAt() {
        return payCreatedAt;
    }
}