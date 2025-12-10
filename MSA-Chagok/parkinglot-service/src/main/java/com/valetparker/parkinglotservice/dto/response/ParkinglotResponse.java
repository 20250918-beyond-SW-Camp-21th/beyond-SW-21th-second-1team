package com.valetparker.parkinglotservice.dto.response;

import com.valetparker.parkinglotservice.domain.ParkingLot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "주차장 상세 응답 DTO")
public class ParkinglotResponse {

    @Schema(description = "주차장 ID")
    private Long parkinglotId;

    @Schema(description = "주차장 이름")
    private String name;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "자치구 (한글명)")
    private String districtName;

    @Schema(description = "남은 주차 자리")
    private int remainingSpots;

    @Schema(description = "전체 주차 자리")
    private int totalSpots;

    @Schema(description = "사용중인 주차 자리")
    private int usedSpots;

    @Schema(description = "기본 요금")
    private Integer baseFee;

    @Schema(description = "기본 시간")
    private Integer baseTime;

    @Schema(description = "단위 요금")
    private Integer unitFee;

    @Schema(description = "단위 시간")
    private Integer unitTime;

    @Schema(description = "평점")
    private Double avgRating;

    public static ParkinglotResponse from(ParkingLot entity) {
        return ParkinglotResponse.builder()
                .parkinglotId(entity.getParkinglotId())
                .name(entity.getName())
                .address(entity.getAddress())
                .districtName(entity.getSeoulDistrict().getKoreanName()) // 한글 이름 사용
                .remainingSpots(entity.getRemainingSpots())
                .totalSpots(entity.getTotalSpots())
                .usedSpots(entity.getUsedSpots())
                .baseFee(entity.getBaseFee())
                .baseTime(entity.getBaseTime())
                .unitFee(entity.getUnitFee())
                .unitTime(entity.getUnitTime())
                .avgRating(entity.getAvgRating())
                .build();
    }
}
