package com.example.parkinglotservice.repository;


import com.example.parkinglotservice.domain.ParkingLot;
import com.example.parkinglotservice.enums.SeoulDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkinglotRepository extends JpaRepository<ParkingLot, Long> {

    List<ParkingLot> findBySeoulDistrict(SeoulDistrict seoulDistrict);
}
