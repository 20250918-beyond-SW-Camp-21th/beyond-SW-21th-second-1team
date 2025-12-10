package com.valetparker.reparkingservice.query.repository;

import com.valetparker.reparkingservice.common.entity.Parkinglot;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkinglotQueryRepository extends JpaRepository<Parkinglot, Long> {

    Page<Parkinglot> findAll(Pageable pageable);

    Page<Parkinglot> findAllBySeoulDistrict(SeoulDistrict seoulDistrict, Pageable pageable);
}
