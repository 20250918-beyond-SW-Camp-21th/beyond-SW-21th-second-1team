package com.valetparker.chagok.reservation.repository;

import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.response.ReservationResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Reservation save(Reservation reservation);

    Optional<Reservation> findByReservationId(Long reservationId);

    List<ReservationResponse> findAllByReservationId(Long reservationId);

    void deleteByReservationId(Long reservationId);
}
