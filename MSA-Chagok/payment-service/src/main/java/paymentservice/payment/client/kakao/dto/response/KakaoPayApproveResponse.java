package paymentservice.payment.client.kakao.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoPayApproveResponse {

    private String aid; // 요청 고유 번호
    private String tid; // 결제 고유 번호
    private String cid; // 가맹점 코드

    @JsonProperty("partner_order_id")
    private String partnerOrderId; // 가맹점 주문 번호

    @JsonProperty("partner_user_id")
    private String partnerUserId; // 가맹점 회원 ID

    @JsonProperty("payment_method_type")
    private String paymentMethodType; // 결제 수단 (CARD/MONEY)

    @JsonProperty("amount")
    private Amount amount; // 결제 금액 정보

    @JsonProperty("item_name")
    private String itemName; // 상품명

    @JsonProperty("item_code")
    private String itemCode; // 상품 코드

    @JsonProperty("quantity")
    private Integer quantity; // 상품 수량

    @JsonProperty("created_at")
    private LocalDateTime createdAt; // 결제 준비 요청 시각

    @JsonProperty("approved_at")
    private LocalDateTime approvedAt; // 결제 승인 시각

    private String payload; // 요청 시 전달한 payload

    @Getter
    @Setter
    @ToString
    public static class Amount {
        private Integer total; // 전체 결제 금액
        @JsonProperty("tax_free")
        private Integer taxFree; // 비과세
        private Integer vat; // 부가세
        private Integer point; // 사용한 포인트
        private Integer discount; // 할인 금액
        @JsonProperty("green_deposit")
        private Integer greenDeposit; // 컵 보증금
    }
}
