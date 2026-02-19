package com.valetparker.chagok.payment.enums;

public enum PaymentStatus {
    INITIAL,            // 초기 생성 (결제 준비 전)
    PENDING_PAYMENT,    // 결제 준비 완료 (결제 대기)
    SUCCESS,            // 결제 완료
    CANCELED,           // 취소 완료
    FAILED              // 결제 실패
}