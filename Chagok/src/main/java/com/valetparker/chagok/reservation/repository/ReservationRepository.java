package com.valetparker.chagok.reservation.repository;


import com.valetparker.chagok.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
