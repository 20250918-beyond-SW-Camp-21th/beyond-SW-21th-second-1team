package com.valetparker.reservationservice.command.service;

import com.valetparker.reservationservice.command.client.ParkingLotClient;
import com.valetparker.reservationservice.command.dto.request.ReservationEndRequest;
import com.valetparker.reservationservice.command.dto.request.ReservationStartRequest;
import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.command.dto.response.UsedSpotsUpdateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.valetparker.reservationservice.command.repository.ReservationCommandRepository;
import lombok.RequiredArgsConstructor;
import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.common.converter.LocalDateTimeConverter;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationCommandRepository reservationCommandRepository;
    private final ParkingLotClient parkingLotClient;
    private final LocalDateTimeConverter localDateTimeConverter;

    @Transactional
    public Long createReservation(ReservationCreateRequest request, Long userNo) {
        LocalDateTime startTime = localDateTimeConverter.convert(request.getStartTime());
        BaseInfoResponse response = parkingLotClient.getParkinglotBaseInfo(request.getParkingLotId());

        int baseTimeMinutes = response.getBaseTime();
        LocalDateTime endTime = startTime.plusMinutes(baseTimeMinutes);

        if (reservationCommandRepository.existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
                // 겹치는 시간이 있을 경우
                response.getParkinglotId(),
                startTime,
                endTime
        )) {
            throw new BusinessException(ErrorCode.REGIST_ERROR_TIME_CONFLICT);
        }

        Reservation newReservation = Reservation.create(
                startTime,
                endTime,
                false,
                LocalDateTime.now(),
                userNo,
                request.getParkingLotId()
        );

        Reservation saved = reservationCommandRepository.save(newReservation);

        return saved.getReservationId();
    }


    // Payment Response 생성
    public PaymentResponse createPayment(Long reservationId) {
        Reservation reservation = reservationCommandRepository.findByReservationIdAndIsCanceledFalse(reservationId);
        BaseInfoResponse parkinglot = parkingLotClient
                .getParkinglotBaseInfo(reservation.getParkinglotId());
        Integer totalAmount = parkinglot.getBaseFee();
        return PaymentResponse.builder()
                .reservationId(reservationId)
                .parkinglotId(parkinglot.getParkinglotId())
                .totalAmount(totalAmount)
                .parkinglotName(parkinglot.getName())
                .build();
    }

    public UsedSpotsUpdateResponse startReservation(ReservationStartRequest request) {
        LocalDateTime currTime = localDateTimeConverter.convert(request.getUpdateTime());
        Reservation reservation = reservationCommandRepository.findByReservationIdAndIsCanceledFalse(request.getReservationId());

        boolean hasReservationStarted = reservation.isStarted(currTime, reservation.getStartTime(), reservation.getEndTime());

        if (!hasReservationStarted) {
            throw new BusinessException(ErrorCode.VALIDATION_ERROR_EARLY_START);
        }
        UsedSpotsUpdateResponse response =  UsedSpotsUpdateResponse.builder()
                .parkinglotId(request.getParkinglotId())
                .using(true)
                .build();
        parkingLotClient.updateUsedSpots(response);
        return response;
    }

    public UsedSpotsUpdateResponse finishReservation(ReservationEndRequest request) {
        UsedSpotsUpdateResponse response = UsedSpotsUpdateResponse.builder()
                .parkinglotId(request.getParkinglotId())
                .using(false)
                .build();
        parkingLotClient.updateUsedSpots(response);
        return response;
    }

}
