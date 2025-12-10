package com.example.parkinglotservice.service;


import com.example.parkinglotservice.domain.ParkingLot;
import com.example.parkinglotservice.dto.request.ParkinglotRequest;
import com.example.parkinglotservice.dto.response.ParkinglotResponse;
import com.example.parkinglotservice.enums.SeoulDistrict;
import com.example.parkinglotservice.repository.ParkinglotRepository;
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
    private final ModelMapper modelMapper;

    // 1. 주차장 등록
    @Transactional
    public ParkinglotResponse createParkingLot(ParkinglotRequest request) {
//        ParkingLot parkingLot = request.toEntity();
//        ParkingLot savedParkingLot = parkinglotRepository.save(parkingLot);  이건 모놀리식때 사용했음.
//        return ParkinglotResponse.from(savedParkingLot);

        // DTO -> Entity 변환
        // (필드명이 같으면 ModelMapper가 알아서 값 복사함.)
        ParkingLot entity = modelMapper.map(request, ParkingLot.class);

        // 2. DB저장
        // (Entity 내부에서 usedSpots = 0 초기화 로직이 동작해야 됨.)
        ParkingLot savedParkingLot = parkinglotRepository.save(entity);

        // 3. Entity -> DTO 변환 후 변환
        return modelMapper.map(savedParkingLot, ParkinglotResponse.class);
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
