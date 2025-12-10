package com.valetparker.paymentservice.domain;

import com.valetparker.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name = "tbl_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tid;

    @Column(nullable = false, unique = true)
    private String partnerOrderId;

    @Column(nullable = false)
    private String partnerUserId;

    private String pgToken;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    private Long reservationId;

    private LocalDateTime approvedAt;

    public void approve(String pgToken, PaymentStatus status) {
        this.pgToken = pgToken;
        this.paymentStatus = status;
        this.approvedAt = LocalDateTime.now();
    }

    public void setReadyInfo(String tid) {
        this.tid = tid;
        this.paymentStatus = PaymentStatus.PENDING_PAYMENT;
    }

}