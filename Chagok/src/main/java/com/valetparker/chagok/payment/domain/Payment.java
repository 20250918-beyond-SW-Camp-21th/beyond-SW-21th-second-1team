package com.valetparker.chagok.payment.domain;

import com.valetparker.chagok.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

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

    private String pgToken;

    @Column(nullable = false)
    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false)
    private Long reservationId;

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setReadyInfo(String tid) {
        this.tid = tid;
        this.status = PaymentStatus.PENDING_PAYMENT;
    }

    public void setPgToken(String pgToken) {
        this.pgToken = pgToken;
    }
}