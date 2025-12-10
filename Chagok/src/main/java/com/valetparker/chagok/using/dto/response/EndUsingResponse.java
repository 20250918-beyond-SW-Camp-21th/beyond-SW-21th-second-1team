package com.valetparker.chagok.using.dto.response;

import com.valetparker.chagok.using.enums.UsingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EndUsingResponse {

    private Long usingId;
    private Long reservationId;
    private UsingStatus usingStatus;     // 최종 상태: USED

    private boolean overdue;            // 연체 여부 (예약시간 이후 종료면 true)
    private long exceededMinutes;       // 초과 사용 시간(분)
    private long exceededUnits;         // unit_time 단위로 몇 번 초과했는지
    private long exceededFee;           // 결제 요청 예정 연체 금액
}
