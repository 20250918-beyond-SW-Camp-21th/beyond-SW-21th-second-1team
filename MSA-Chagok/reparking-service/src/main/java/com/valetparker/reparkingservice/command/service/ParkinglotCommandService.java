package com.valetparker.reparkingservice.command.service;

import com.valetparker.reparkingservice.command.dto.ParkinglotCreateRequest;
import com.valetparker.reparkingservice.command.dto.ParkinglotUpdateRequest;
import com.valetparker.reparkingservice.command.repository.ParkinglotCommandRepository;
import com.valetparker.reparkingservice.common.entity.Parkinglot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ParkinglotCommandService {

    ParkinglotCommandRepository repository;

    @Transactional
    public Long createParkinglot(ParkinglotCreateRequest request) {
        Parkinglot newParkinglot = Parkinglot.create(
                request.getName(),
                request.getAddress(),
                request.getSeoulDistrict(),
                request.getTotalSpots(),
                request.getBaseFee(),
                request.getUnitFee()
        );
        Parkinglot saved = repository.save(newParkinglot);
        return saved.getParkinglotId();
    }

//    @Transactional
//    public void updateParkinglot(ParkinglotUpdateRequest request, Long parkinglotId) {
//
//
//    }


}
