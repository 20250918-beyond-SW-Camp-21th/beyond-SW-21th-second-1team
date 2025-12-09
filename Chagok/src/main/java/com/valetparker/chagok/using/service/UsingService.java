package com.valetparker.chagok.using.service;

import com.valetparker.chagok.common.exception.BusinessException;
import com.valetparker.chagok.common.exception.ErrorCode;
import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import com.valetparker.chagok.using.domain.Using;
import com.valetparker.chagok.using.dto.request.UsingInfoRequest;
import com.valetparker.chagok.using.dto.response.EndUsingResponse;
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
        UsingStatus status = using.getUsingStatus();

        // 이미 USED면 그대로 출력
        if (status == UsingStatus.USED) {
            if (using.getExceededCount() > 0) {
                int unitFee = parkinglot.getUnitFee();
                exceededUnits = using.getExceededCount();
                exceededFee = exceededUnits * unitFee;
            }
        }

        // 연체된 경우
        else if (status == UsingStatus.EXCEEDED_USING || using.getExceededCount() > 0) {
            int unitTime = parkinglot.getUnitTime();
            int unitFee = parkinglot.getUnitFee();

            // DB에 저장된 exceededCount 기반
            exceededUnits = using.getExceededCount();
            exceededMinutes = exceededUnits * (long) unitTime;
            exceededFee = exceededUnits * (long) unitFee;
        }

        // BEFORE / USING 상태만 "시간 기반"으로 상태 업데이트
        else {
            if (now.isBefore(startTime)) {
                status = UsingStatus.BEFORE;
            } else if (!now.isAfter(endTime)) {
                status = UsingStatus.USING;
            } else {
                // 이제 막 연체 상태로 넘어간 경우
                status = UsingStatus.EXCEEDED_USING;

                exceededMinutes = Duration.between(endTime, now).toMinutes();
                int unitTime = parkinglot.getUnitTime();
                int unitFee = parkinglot.getUnitFee();

                exceededUnits = (exceededMinutes + unitTime - 1) / unitTime;
                exceededFee = exceededUnits * unitFee;

                using.setUsingStatus(UsingStatus.EXCEEDED_USING);
                using.exceededCounter((int) exceededUnits);
            }

            // 상태가 변경되었으면 반영

            using.settingUsingStatus(status);


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

    /**
     * 이용 종료
     * - 예약 시간 내 종료: 연체 없음
     * - 예약 시간 이후 종료: 연체 있음 (unit_time, unit_fee 기준 계산)
     * - 공통: Using 상태 USED, is_quit = true, parkinglot.usedSpots - 1
     */
    @Transactional
    public EndUsingResponse endUsing(Long reservationId) {

        log.info("[UsingService] 이용 종료 요청. reservationId={}", reservationId);

        // Using 조회
        Using using = usingRepository.findByReservationId(reservationId)
                .orElseThrow(() -> {
                    log.warn("Using 정보 없음. reservationId={}", reservationId);
                    return new BusinessException(ErrorCode.NOT_FOUND);
                });

        // 이미 종료된 건이면 종료 불가
        if (using.getUsingStatus() == UsingStatus.USED) {
            log.warn("이미 종료된 이용 건입니다. usingId={}", using.getUsingId());
            throw new BusinessException(ErrorCode.VALIDATION_ERROR);
        }

        // Reservation 조회
        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        // Parkinglot 조회
        ParkingLot parkinglot = parkinglotRepository.findById(reservation.getParkinglotId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = reservation.getEndTime();

        boolean overdue = false;
        long exceededMinutes = 0L;
        long exceededUnits = 0L;
        long exceededFee = 0L;

        // 예약 시간 이후 종료인지 판단
        if (now.isAfter(endTime)) {
            overdue = true;

            exceededMinutes = Duration.between(endTime, now).toMinutes();
            if (exceededMinutes < 0) exceededMinutes = 0;

            int unitTime = parkinglot.getUnitTime();   // 예: 60분
            int unitFee = parkinglot.getUnitFee();     // 예: 1000원

            if (unitTime > 0 && exceededMinutes > 0) {
                // 올림 나눗셈: 1분이라도 초과하면 1단위로 계산
                exceededUnits = (exceededMinutes + unitTime - 1) / unitTime;
                exceededFee = exceededUnits * unitFee;
            }

            // 연체 횟수는 unit 기준 초과 횟수로 저장
            using.exceededCounter((int) exceededUnits);
            log.info("연체 종료. exceededMinutes={}, exceededUnits={}, exceededFee={}",
                    exceededMinutes, exceededUnits, exceededFee);
        } else {
            log.info("예약 시간 내 정상 종료. now={}, endTime={}", now, endTime);
        }

        // 공통 종료 처리: 상태 USED, 출차 처리
        using.setUsingStatus(UsingStatus.USED);
        using.setIsQuit(true);   // is_quit = 1

        // 주차장 자리 하나 늘리기 (usedSpots 감소)

        log.info("[UsingService] 이용 종료 완료. usingId={}, reservationId={}, overdue={}",
                using.getUsingId(), reservationId, overdue);

        // JPA 더티체킹으로 using, parkinglot 모두 UPDATE 반영됨

        return new EndUsingResponse(
                using.getUsingId(),
                reservation.getReservationId(),
                using.getUsingStatus(),
                overdue,
                exceededMinutes,
                exceededUnits,
                exceededFee
        );
    }

}
