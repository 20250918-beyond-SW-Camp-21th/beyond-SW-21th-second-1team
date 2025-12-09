package com.valetparker.chagok.using.service;

import com.valetparker.chagok.common.exception.BusinessException;
import com.valetparker.chagok.common.exception.ErrorCode;
import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import com.valetparker.chagok.using.domain.Using;
import com.valetparker.chagok.using.dto.request.UsingInfoRequest;
import com.valetparker.chagok.using.dto.response.UsingInfoResponse;
import com.valetparker.chagok.using.enums.UsingStatus;
import com.valetparker.chagok.using.repository.UsingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsingService {

    private final UsingRepository usingRepository;
    private final ReservationRepository reservationRepository;
    private final ParkinglotRepository parkinglotRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Long createUsing(Long reservationId) {

        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        // 이미 Using 이 존재하면 중복 생성 방지
        usingRepository.findByReservationId(reservationId)
                .ifPresent(u -> {
                    throw new BusinessException(ErrorCode.REGIST_ERROR); // “이미 존재합니다” 등
                });

        Using using = Using.builder()
                .reservationId(reservationId)
                .usingStatus(UsingStatus.BEFORE)
                .exceededCount(0)
                .build();

        Using saved = usingRepository.save(using);

        return saved.getUsingId();
    }


    @Transactional
    public UsingInfoResponse getUsingInfo(UsingInfoRequest request) {

        // Using 의 Reservation 확인
        Using using = usingRepository.findByReservationId(request.getReservationId())
                .orElseThrow(() -> {
                    log.warn("Using 정보 없음 → reservationId={}", request.getReservationId());
                    return new BusinessException(ErrorCode.NOT_FOUND);
                });

        // Reservation ID 확인
        Reservation reservation = reservationRepository.findByReservationId(request.getReservationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        // Parkinglot ID 확인
        ParkingLot parkinglot = parkinglotRepository.findById(reservation.getParkinglotId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        LocalDateTime now = request.getCurrTime();  // 현 시간(테스트할 시간)
        LocalDateTime startTime = reservation.getStartTime();   // 예약 시작시간
        LocalDateTime endTime = reservation.getEndTime();       // 얘역 종료시간

        log.info("예약 시간: {}, end: {}", startTime, endTime);

        // 공통 자리 정보
        int totalSpots = parkinglot.getTotalSpots();
        int usedSpots = parkinglot.getUsedSpots();
        int availableSpots = totalSpots - usedSpots;

        // 잔여 시간 계산 (0 아래로 내려가지 않게 보정)
        long remainingMinutes = Duration.between(now, endTime).toMinutes();
        if (remainingMinutes < 0) remainingMinutes = 0;

        // 기본값
        long exceededMinutes = 0;
        long exceededUnits = 0;
        long exceededFee = 0;

        // 4. 상태 계산
        UsingStatus status;
        if (using.getUsingStatus() == UsingStatus.USED) {
            // 이미 이용 종료된 건
            status = UsingStatus.USED;

        } else if (now.isBefore(startTime)) {
            // 예약 시작 전
            status = UsingStatus.BEFORE;

        } else if (now.isBefore(endTime)) {
            // 예약 시간 사이
            status = UsingStatus.USING;

        } else {
            // 예약 종료 시각 이후 (아직 USED로 안 바뀐 경우) → 연체
            status = UsingStatus.EXCEEDED_USING;

            // 연체 시간(분)
            exceededMinutes = Duration.between(endTime, now).toMinutes();

            // unitTime / unitFee 기준으로 연체 단위, 금액 계산
            int unitTime = parkinglot.getUnitTime();    // 예: 10분, 60분 등
            int unitFee = parkinglot.getUnitFee();      // 예: 500원

            // 올림 나눗셈으로 몇 단위 초과인지 계산
            exceededUnits = (exceededMinutes + unitTime - 1) / unitTime;

            exceededFee = exceededUnits * unitFee;

            // 필요하면 Using 엔티티에도 연체 횟수 누적
            using.setUsingStatus(UsingStatus.EXCEEDED_USING);
            using.setExceededCount((int) exceededUnits);
        }

        // 상태가 변경되었으면 반영
        if (using.getUsingStatus() != status) {
            using.setUsingStatus(status);
        }

        return new UsingInfoResponse(
                using.getUsingId(),
                reservation.getReservationId(),
                status,
                using.getExceededCount(),
                parkinglot.getName(),
                totalSpots,
                usedSpots,
                availableSpots,
                startTime,
                endTime,
                remainingMinutes,
                exceededMinutes,
                exceededUnits,
                exceededFee
        );
    }

}
