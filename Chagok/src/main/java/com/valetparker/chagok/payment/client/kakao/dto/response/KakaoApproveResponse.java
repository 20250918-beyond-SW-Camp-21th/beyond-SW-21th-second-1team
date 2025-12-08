package com.valetparker.chagok.payment.client.kakao.dto.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoApproveResponse {
    private String aid;              // 요청 고유 번호
    private String tid;              // 결제 고유 번호
    private String partner_order_id; // 우리 시스템 주문 번호
    private String item_name;        // (n시간 권)
    private String created_at;
    private Amount amount;           // 결제 금액 정보 (내부 클래스)

    @Getter
    public static class Amount {
        private Integer total;
    }
}
