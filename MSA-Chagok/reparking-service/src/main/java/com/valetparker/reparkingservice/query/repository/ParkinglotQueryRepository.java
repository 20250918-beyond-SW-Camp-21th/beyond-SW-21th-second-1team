package com.valetparker.reparkingservice.query.repository;

import com.valetparker.reparkingservice.common.entity.Parkinglot;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkinglotQueryRepository extends JpaRepository<Parkinglot, Long> {

    Page<Parkinglot> findAllByParkinglotId(Pageable pageable);

    Page<Parkinglot> findAllBySeoulDistrict(SeoulDistrict seoulDistrict, Pageable pageable);
}
