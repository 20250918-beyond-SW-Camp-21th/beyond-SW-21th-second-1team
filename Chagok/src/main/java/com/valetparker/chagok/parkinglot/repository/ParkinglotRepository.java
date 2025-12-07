package com.valetparker.chagok.parkinglot.repository;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkinglotRepository extends JpaRepository<ParkingLot, Long> {
    // 특정 구 주차장 목록 검색
    List<ParkingLot> findBySeoulDistrict(Seouldistrict seoulDistrict);

    // 주차장 이름으로 검색
    List<ParkingLot> findByNameContaining(String name);

}
