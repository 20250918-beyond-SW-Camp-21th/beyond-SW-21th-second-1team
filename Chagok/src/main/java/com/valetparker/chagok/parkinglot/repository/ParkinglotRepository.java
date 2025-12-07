package com.valetparker.chagok.parkinglot.repository;

import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.enums.SeoulDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkinglotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findBySeoulDistrict(SeoulDistrict seoulDistrict);
}
