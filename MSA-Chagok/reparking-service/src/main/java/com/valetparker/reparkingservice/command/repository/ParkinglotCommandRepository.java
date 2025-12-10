package com.valetparker.reparkingservice.command.repository;

import com.valetparker.reparkingservice.common.entity.Parkinglot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkinglotCommandRepository extends JpaRepository<Parkinglot, Long> {

    Parkinglot save(Parkinglot parkinglot);

    Optional<Parkinglot> findById(Long parkinglotId);

    void deleteById(Long partkinglotId);
}
