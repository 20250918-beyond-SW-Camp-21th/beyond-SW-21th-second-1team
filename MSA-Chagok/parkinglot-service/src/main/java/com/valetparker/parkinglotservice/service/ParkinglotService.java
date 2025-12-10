package com.valetparker.parkinglotservice.service;


import com.valetparker.parkinglotservice.domain.ParkingLot;
import com.valetparker.parkinglotservice.dto.request.ParkinglotRequest;
import com.valetparker.parkinglotservice.dto.response.ParkinglotResponse;
import com.valetparker.parkinglotservice.enums.SeoulDistrict;
import com.valetparker.parkinglotservice.repository.ParkinglotRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkinglotService {

    private final ParkinglotRepository parkinglotRepository;

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
