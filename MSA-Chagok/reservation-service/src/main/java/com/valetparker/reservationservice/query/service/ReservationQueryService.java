package com.valetparker.reservationservice.query.service;

import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import com.valetparker.reservationservice.common.dto.ReservationDto;
import com.valetparker.reservationservice.query.dto.response.ReservationListResponse;
import com.valetparker.reservationservice.query.dto.response.ReservationQueryResponse;
import com.valetparker.reservationservice.query.repository.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;

    // 단일객체 조회(reservationId)
    @Transactional(readOnly = true)
    public ReservationQueryResponse getReservationDetailBy(Long reservationId) {
        Reservation reservation = reservationQueryRepository
                .findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        ReservationDto response = ReservationDto.from(reservation);
        return ReservationQueryResponse.builder()
                .reservationDto(response)
                .build();
    }

    // 리스트 객체 조회(userNo)
    @Transactional(readOnly = true)
    public ReservationListResponse getReservationsByUserNo(Long userNo) {
        List<Reservation> reservations = reservationQueryRepository
                .findAllByUserNoOrderByCreatedAtDesc(userNo);
        List<ReservationDto> reservationDtos = reservations.stream()
                .map(ReservationDto::from)
                .toList();
        return ReservationListResponse.builder()
                .reservationDtoList(reservationDtos)
                .build();

    }
}
