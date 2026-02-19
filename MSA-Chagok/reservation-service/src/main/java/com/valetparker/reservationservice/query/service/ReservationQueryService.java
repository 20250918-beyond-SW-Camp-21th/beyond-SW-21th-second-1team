package com.valetparker.reservationservice.query.service;

import com.valetparker.reservationservice.command.client.ParkingLotClient;
import com.valetparker.reservationservice.command.dto.response.BaseInfoResponse;
import com.valetparker.reservationservice.command.dto.response.PaymentResponse;
import com.valetparker.reservationservice.common.dto.ApiResponse;
import com.valetparker.reservationservice.common.entity.Reservation;
import com.valetparker.reservationservice.common.exception.BusinessException;
import com.valetparker.reservationservice.common.exception.ErrorCode;
import com.valetparker.reservationservice.common.dto.ReservationDto;
import com.valetparker.reservationservice.query.dto.response.ReservationListResponse;
import com.valetparker.reservationservice.query.dto.response.ReservationQueryResponse;
import com.valetparker.reservationservice.query.repository.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;
    private final ParkingLotClient parkingLotClient;

    // 단일객체 조회(reservationId)
    @Transactional(readOnly = true)
    public ReservationQueryResponse getReservationDetailBy(Long reservationId) {

        Reservation reservation = reservationQueryRepository
                .findByReservationId(reservationId);
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
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

    public Reservation getByReservationId(Long reservationId) {
        return reservationQueryRepository.findByReservationId(reservationId);
//                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
    }

    // Payment API service
    public PaymentResponse getInfoForPaymentReservation(Long reservationId) {
        Reservation reservation = reservationQueryRepository.findByReservationId(reservationId);
//        Reservation entityReservation = reservation.orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        ResponseEntity<ApiResponse<BaseInfoResponse>> responseEntity =
                parkingLotClient.getParkinglotBaseInfo(reservation.getParkinglotId());

        ApiResponse<BaseInfoResponse> body = responseEntity.getBody();

        // 1단계: body 자체 검증
        if (body == null) {
            throw new BusinessException(ErrorCode.REGIST_ERROR_NO_PARKINGLOT);
        }

        // 2단계: success 여부 검증
        if (!body.isSuccess()) {
            // 필요하면 errorCode 보고 분기
            throw new BusinessException(ErrorCode.REGIST_ERROR_NO_PARKINGLOT);
        }

        // 3단계: data 존재 여부 검증
        BaseInfoResponse info = body.getData();
        if (info == null) {
            throw new BusinessException(ErrorCode.REGIST_ERROR_NO_PARKINGLOT);
        }

        // 4단계: 이제 안심하고 사용
        Integer amount = info.getBaseFee();
        String name = info.getName();


        return PaymentResponse.builder()
                .parkinglotId(reservation.getParkinglotId())
                .reservationId(reservationId)
                .parkinglotName(name)
                .totalAmount(amount)
                .build();
    }
}
