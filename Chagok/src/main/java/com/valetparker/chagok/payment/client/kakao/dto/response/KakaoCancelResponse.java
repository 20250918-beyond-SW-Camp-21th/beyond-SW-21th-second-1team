package com.valetparker.chagok.payment.client.kakao.dto.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoCancelResponse {
    private String tid;
    private String status;
    private String cancelled_at;
    private Amount amount;          // 취소된 금액 정보

    @Getter
    public static class Amount {
        private Integer total;
        private Integer tax_free;
        private Integer vat;        // 부가세
        private Integer point;      // 포인트 사용 금액
    }
}
