package com.valetparker.chagok.parkinglot.dto.response;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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

    @Schema(description = "기본 요금")
    private Integer baseFee;

    @Schema(description = "단위 요금")
    private Integer unitFee;

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
                .baseFee(entity.getBaseFee())
                .unitFee(entity.getUnitFee())
                .avgRating(entity.getAvgRating())
                .build();
    }
}
