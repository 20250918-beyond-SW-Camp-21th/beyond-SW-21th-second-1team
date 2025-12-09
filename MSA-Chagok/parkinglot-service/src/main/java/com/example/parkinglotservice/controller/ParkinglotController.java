package com.example.parkinglotservice.controller;


import com.example.parkinglotservice.dto.request.ParkinglotRequest;
import com.example.parkinglotservice.dto.response.ParkinglotResponse;
import com.example.parkinglotservice.enums.SeoulDistrict;
import com.example.parkinglotservice.service.ParkinglotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-lots")
@RequiredArgsConstructor
@Tag(name = "ParkingLot API", description = "주차장 관리 API")
public class ParkinglotController {

    private final ParkinglotService parkinglotService;

    @PostMapping
    @Operation(summary = "주차장 등록", description = "새로운 주차장을 등록합니다.")
    public ParkinglotResponse createParkingLot(@RequestBody ParkinglotRequest request) {
        return parkinglotService.createParkingLot(request);
    }

    @GetMapping
    @Operation(summary = "전체 주차장 조회", description = "모든 주차장 목록을 조회합니다.")
    public List<ParkinglotResponse> getAllParkingLots() {
        return parkinglotService.getAllParkingLots();
    }

    @GetMapping("/search")
    @Operation(summary = "지역별 주차장 검색", description = "특정 구(예: GANGNAM)의 주차장만 조회합니다.")
    public List<ParkinglotResponse> searchParkingLots(@RequestParam SeoulDistrict district) {
        return parkinglotService.getParkingLotsByDistrict(district);
    }

    @PostMapping
    public ResponseEntity<ParkinglotResponse> create(@RequestBody ParkinglotRequest request) {
        ParkinglotResponse response = parkinglotService.createParkingLot(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }





}