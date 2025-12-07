package com.valetparker.chagok.parkinglot.dto;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "주차장 조회 응답 DTO")
public class ParkinglotResponse {

    @Schema(description = "주차장 ID")
    private Long id;

    @Schema(description = "주차장 이름")
    private String name;

    @Schema(description = "주소")
    private String address;

    @Schema(description = "행정구")
    private Seouldistrict seoulDistrict;

    @Schema(description = "전체 주차 면수")
    private Integer totalSpots;

    @Schema(description = "현재 사용중인 면수")
    private Integer usedSpots;

    @Schema(description = "잔여 주차 면수")
    private Integer remainingSpots;

    @Schema(description = "기본 요금")
    private Integer baseFee;

    @Schema(description = "기본 시간(분)")
    private Integer baseTime;

    @Schema(description = "단위 요금")
    private Integer unitFee;

    @Schema(description = "단위 시간(분)")
    private Integer unitTime;

    @Schema(description = "평점")
    private Double averageRating;

    public ParkinglotResponse(ParkingLot parkinglot) {
        this.id = parkinglot.getParkinglotId();
        this.name = parkinglot.getName();
        this.address = parkinglot.getAddress();
        this.seoulDistrict = parkinglot.getSeoulDistrict();
        this.totalSpots = parkinglot.getTotalSpots();
        this.usedSpots = parkinglot.getUsedSpots();
        this.remainingSpots = parkinglot.getRemainingSpots();
        this.baseFee = parkinglot.getBaseFee();
        this.baseTime = parkinglot.getBaseTime();
        this.unitFee = parkinglot.getUnitFee();
        this.unitTime = parkinglot.getUnitTime();
        this.averageRating = parkinglot.getAverageRating();
    }


    public static ParkinglotResponse from(ParkingLot parkinglot) {
        return new ParkinglotResponse(parkinglot);
    }
}
