package com.example.parkinglotservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "주차장 목록 조회 응답 (Wrapper)")
public class ParkinglotListResponse {

    @Schema(description = "주차장 목록 데이터")
    private List<ParkinglotResponse> parkingLots;

    @Schema(description = "조회된 주차장 총 개수", example = "5")
    private int count; // 리스트의 크기 (size)

    // 생성자: 리스트를 받으면 개수는 자동으로 세서 넣음
    public ParkinglotListResponse(List<ParkinglotResponse> parkingLots) {
        this.parkingLots = parkingLots;
        this.count = parkingLots.size();
    }

    // 편하게 쓰기 위한 스태틱 메서드
    public static ParkinglotListResponse from(List<ParkinglotResponse> list) {
        return new ParkinglotListResponse(list);
    }
}
