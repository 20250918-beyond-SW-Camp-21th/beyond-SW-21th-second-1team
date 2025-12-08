package com.valetparker.chagok.parkinglot.dto.request;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.enums.SeoulDistrict;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "주차장 등록 요청 DTO")
public class ParkinglotRequest {

    @Schema(description = "주차장 이름", example = "차곡 강남 주차장")
    private String name;

    @Schema(description = "주소", example = "서울시 강남구 테헤란로 123")
    private String address;

    @Schema(description = "자치구 (영어 대문자로 입력)", example = "GANGNAM")
    private SeoulDistrict seoulDistrict;

    @Schema(description = "전체 주차 면수", example = "100")
    private Integer totalSpots;

    @Schema(description = "기본 요금 (원)", example = "3000")
    private Integer baseFee;

    @Schema(description = "단위 요금 (원)", example = "1000")
    private Integer unitFee;

    public ParkingLot toEntity() {
        return ParkingLot.builder()
                .name(this.name)
                .address(this.address)
                .seoulDistrict(this.seoulDistrict)
                .totalSpots(this.totalSpots)
                .baseFee(this.baseFee)
                .unitFee(this.unitFee)
                // baseTime(30), unitTime(10) 등은 Entity 빌더 기본값 사용
                .build();
    }
}
