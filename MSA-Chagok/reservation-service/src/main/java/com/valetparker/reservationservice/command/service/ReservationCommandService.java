package com.valetparker.reservationservice.command.service;

import com.valetparker.reservationservice.command.client.ParkingLotClient;
import com.valetparker.reservationservice.command.client.UserClient;
import com.valetparker.reservationservice.command.dto.request.ReservationCreateRequest;
import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.command.repository.ReservationCommandRepository;
import com.valetparker.reservationservice.common.converter.LocalDateTimeConverter;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ReservationCommandService {

    private ReservationCommandRepository reservationCommandRepository;
    private UserClient userClient;
    private ParkingLotClient parkingLotClient;
    private LocalDateTimeConverter localDateTimeConverter;

    @Transactional
    public Long createReservation(ReservationCreateRequest request, String userId) {
        LocalDateTime startTime = localDateTimeConverter.convert(request.getStartTime());
        Long userNo = userClient.getUserNo(userId);

        ResponseEntity<ApiResponse<BaseInfoResponse>> response = parkingLotClient.getParkinglotBaseInfo(request.getParkingLotId());


        int baseTimeMinutes = response.getBody().getData().getBaseTime();
        LocalDateTime endTime = startTime.plusMinutes(baseTimeMinutes);

        if (reservationCommandRepository.existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
                // 겹치는 시간이 있을 경우
                response.getBody().getData().getParkinglotId(),
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
        // parkinglot API 메소드 사용 (Count ++)


}
