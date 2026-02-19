package com.valetparker.chagok.parkinglot.service;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.dto.request.ParkinglotRequest;
import com.valetparker.chagok.parkinglot.dto.response.ParkinglotResponse;
import com.valetparker.chagok.parkinglot.enums.SeoulDistrict;
import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkinglotService {

    private final ParkinglotRepository parkinglotRepository;

    // 1. 주차장 등록
    @Transactional
    public ParkinglotResponse createParkingLot(ParkinglotRequest request) {
        ParkingLot parkingLot = request.toEntity();
        ParkingLot savedParkingLot = parkinglotRepository.save(parkingLot);
        return ParkinglotResponse.from(savedParkingLot);
    }

    // 2. 전체 주차장 조회
    public List<ParkinglotResponse> getAllParkingLots() {
        return parkinglotRepository.findAll().stream()
                .map(ParkinglotResponse::from)
                .collect(Collectors.toList());
    }

    // 3. 구(District)별 주차장 검색
    public List<ParkinglotResponse> getParkingLotsByDistrict(SeoulDistrict district) {
        return parkinglotRepository.findBySeoulDistrict(district).stream()
                .map(ParkinglotResponse::from)
                .collect(Collectors.toList());
    }
}
