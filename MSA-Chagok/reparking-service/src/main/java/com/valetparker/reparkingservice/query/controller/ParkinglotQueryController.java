package com.valetparker.reparkingservice.query.controller;

import com.valetparker.reparkingservice.common.dto.ApiResponse;
import com.valetparker.reparkingservice.common.dto.ParkinglotDto;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import com.valetparker.reparkingservice.query.dto.BaseInfoResponse;
import com.valetparker.reparkingservice.query.dto.ParkinglotDetailResponse;
import com.valetparker.reparkingservice.query.dto.ParkinglotListResponse;
import com.valetparker.reparkingservice.query.dto.ParkinglotSearchRequest;
import com.valetparker.reparkingservice.query.enums.ParkinglotSortType;
import com.valetparker.reparkingservice.query.service.ParkinglotQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkinglotQueryController {

    private final ParkinglotQueryService parkinglotQueryService;

    // 1. 주차장 상세 조회
    @GetMapping("/parkinglot/detail/{parkinglotId}")
    public ResponseEntity<ApiResponse<ParkinglotDetailResponse>> getParkinglot(@PathVariable Long parkinglotId) {
        ParkinglotDetailResponse response = parkinglotQueryService.getOneParkinglot(parkinglotId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 2. 주차장 전체 조회 (이름순/평균별점높은순/평균별점낮은순/남은자리많은순)
    //                  + (서울시구 필터)
    @GetMapping("/parkinglots/")
    public ResponseEntity<ApiResponse<ParkinglotListResponse>> getSortedParkinglots(
//            ParkinglotSearchRequest request
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "NAME_ASC") ParkinglotSortType sort,
            @RequestParam(required = false) SeoulDistrict seoulDistrict
    ) {

        ParkinglotSearchRequest request = new ParkinglotSearchRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSort(sort);
        request.setSeoulDistrict(seoulDistrict);

        ParkinglotListResponse response = parkinglotQueryService.getParkinglots(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/parkinglot/base")
    public ResponseEntity<ApiResponse<BaseInfoResponse>> getParkinglotBaseInfo(
            @RequestParam Long parkinglotId
    ) {
        ParkinglotDetailResponse oneDetail = parkinglotQueryService.getOneParkinglot(parkinglotId);
        ParkinglotDto parkDto = oneDetail.getParkinglotDto();
        BaseInfoResponse response = BaseInfoResponse.builder()
                .parkinglotId(parkDto.getParkinglotId())
                .name(parkDto.getName())
                .baseFee(parkDto.getBaseFee())
                .baseTime(parkDto.getBaseTime())
                .build();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
