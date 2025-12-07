package com.valetparker.chagok.parkinglot.dto.response;

import com.valetparker.chagok.parkinglot.dto.ParkinglotResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "주차장 목록 조회 응답 DTO")
public class ParkinglotListResponse {

    @Schema(description = "주차장 목록 데이터")
    private List<ParkinglotResponse> parkingLots;

    @Schema(description = "조회된 주차장 총 개수", example = "3")
    private Integer totalSpots;

    public ParkinglotListResponse(List<ParkinglotResponse> parkingLots) {
        this.parkingLots = parkingLots;
        this.totalSpots = parkingLots.size();
    }

    public static ParkinglotListResponse from(List<ParkinglotResponse> parkingLots) {
        return new ParkinglotListResponse(parkingLots);
    }
}
