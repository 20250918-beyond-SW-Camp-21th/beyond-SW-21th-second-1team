package com.valetparker.chagok.reservation.repository;

import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.dto.response.ReservationResponse;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findByReservationId(Long reservationId);

    List<Reservation> findByUserNoOrderByCreatedAtDesc(Long userNo);

    void deleteByReservationId(Long reservationId);

}
