package com.valetparker.chagok.reservation.repository;

import com.valetparker.chagok.reservation.domain.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {


}
