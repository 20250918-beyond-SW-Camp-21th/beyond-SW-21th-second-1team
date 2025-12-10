package paymentservice.using.dto.response;

import com.valetparker.chagok.using.enums.UsingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsingInfoResponse {

    // 공통 영역
    private long usingId;
    private long reservationId;
    private UsingStatus usingStatus;
    private int exceededCount;          // EXCEEDED_USING 일 때 연장/연체 횟수

    private String parkinglotName;
    private int totalSpots;
    private int usedSpots;
    private int availableSpots;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private long remainingMinutes;      // 예약 종료까지 남은 시간(분), 이미 지났으면 0

    // 연체 정보 영역 (EXCEEDED_USING 일 때만 의미 있음)
    private long exceededMinutes;       // 얼마나 초과되었는지(분)
    private long exceededUnits;         // unit_time 몇 번 초과했는지
    private long exceededFee;           // unit_fee * exceededUnits
}
