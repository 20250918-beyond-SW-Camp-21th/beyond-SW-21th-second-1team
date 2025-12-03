package com.valetparker.chagok.payment.enums;

public enum PaymentStatus {
    READY,          // 결제 준비 요청 상태
    SUCCESS,        // 결제 승인 완료
    CANCEL,         // 결제 취소 발생 (예약 취소)
    FAILED          // 결제 실패
}
