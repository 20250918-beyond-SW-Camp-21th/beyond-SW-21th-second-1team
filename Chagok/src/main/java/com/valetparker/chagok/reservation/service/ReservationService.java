package com.valetparker.chagok.reservation.service;

import com.valetparker.chagok.parkinglot.domain.Parkinglot;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.dto.request.ReservationCreateRequest;
import com.valetparker.chagok.reservation.dto.response.ReservationResponse;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import com.valetparker.chagok.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long createReservation(ReservationCreateRequest request) {

        Reservation reservation = Reservation.builder()
                .partnerOrderId("partnerOrderId01")
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .isCanceled(false)
                .createdAt(LocalDateTime.now())
                .userNo(request.getUserNo())
                .parkinglotId(request.getParkinglotId())
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationDto reservationDto = modelMapper.map(savedReservation, ReservationDto.class);
        Long reservationId = reservationDto.getReservationId();

        return reservationId;
    }

    public List<ReservationDto> getReservationHistory(Long userNo) {
        List<ReservationResponse> reservations = reservationRepository
                .findByUserNoOrderByCreatedAtDesc(userNo);

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ReservationDto.class))
                .toList();
    }
}
