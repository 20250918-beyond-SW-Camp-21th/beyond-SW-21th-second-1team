package com.valetparker.reservationservice.query.service;

import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import com.valetparker.reservationservice.common.dto.ReservationDto;
import com.valetparker.reservationservice.query.dto.response.ReservationDetailResponse;
import com.valetparker.reservationservice.query.repository.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;

    // 단일객체 조회(reservationId)
    @Transactional(readOnly = true)
    public ReservationDetailResponse getReservationDetailBy(Long reservationId) {
        Reservation reservation = reservationQueryRepository
                .findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        ReservationDto response = ReservationDto.from(reservation);
        return ReservationDetailResponse.builder()
                .reservationDto(response)
                .build();
    }
}
