package paymentservice.reservation.service;

import com.valetparker.chagok.common.exception.BusinessException;
import com.valetparker.chagok.common.exception.ErrorCode;
import com.valetparker.chagok.parkinglot.domain.ParkingLot;
import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
import com.valetparker.chagok.reservation.domain.Reservation;
import com.valetparker.chagok.reservation.dto.ReservationDto;
import com.valetparker.chagok.reservation.dto.request.ReservationCreateRequest;
import com.valetparker.chagok.reservation.dto.response.ReservationDetailResponse;
import com.valetparker.chagok.reservation.dto.response.ReservationListResponse;
import com.valetparker.chagok.reservation.repository.ReservationRepository;
import com.valetparker.chagok.user.command.domain.User;
import com.valetparker.chagok.user.command.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ParkinglotRepository parkinglotRepository;
    private final ReservationRepository reservationRepository;
    private final ModelMapper modelMapper;

    // partnerOrderId 포맷
    private static final DateTimeFormatter ORDER_ID_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    // unique partnerOrderId 생성
    private String generateUniquePartnerOrderId() {
        String orderId;

        do {
            String timePart = LocalDateTime.now().format(ORDER_ID_TIME_FORMATTER);
            int random = ThreadLocalRandom.current().nextInt(0, 1000); // 0~999
            String randomPart = String.format("%03d", random);

            // 최종 형식: ORDER_20251209123015999_123
            orderId = "ORDER_" + timePart + "_" + randomPart;

        } while (reservationRepository.existsByPartnerOrderId(orderId));

        return orderId;
    }

    @Transactional
    public Long createReservation(ReservationCreateRequest request) {

        /* 유저 확인(로그인 후 전달된 userNo) */
        User user = userRepository.findByUserNo(request.getUserNo())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        /* 주차장 조회 */
        ParkingLot parkinglot = parkinglotRepository.findById(request.getParkinglotId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        /* 사용자가 예약 등록 시 입력한 시작시간 받아오기 */
        LocalDateTime startTime = request.getStartTime();

         /* 주차장 BaseTime만큼 자동으로 EndTime계산
         *      tbl_parkinglot.base_time = (INT) 30 (예시임)*/
        int baseTimeMinutes = parkinglot.getBaseTime();
        LocalDateTime endTime = startTime.plusMinutes(baseTimeMinutes);

        /* 예약 가능 여부 체크: 남은 자리가 있는가?
        *  total_spots > used_spots 이어야 예약 가능 */
        if (parkinglot.getUsedSpots() >= parkinglot.getTotalSpots()) {
            //자리가 없을 경우
            throw new BusinessException(ErrorCode.REGIST_ERROR);
        }

        /* 예약 가능 여부 체크 해당 시간대에 겹치는 예약이 있는가? */
        boolean hasOverlap = reservationRepository
                .existsByParkinglotIdAndIsCanceledFalseAndEndTimeGreaterThanAndStartTimeLessThan(
                        parkinglot.getParkinglotId(),
                        startTime,
                        endTime
                );

        if (hasOverlap) {
            throw new BusinessException(ErrorCode.REGIST_ERROR);
        }

        // 유니크한 주문번호 생성
        String partnerOrderId = generateUniquePartnerOrderId();

        // 예약 엔티티 생성 (partnerOrderId 포함)
        Reservation reservation = Reservation.builder()
                .partnerOrderId(partnerOrderId)
                .startTime(startTime)
                .endTime(endTime)
                .isCanceled(false)
                .createdAt(LocalDateTime.now())
                .userNo(request.getUserNo())
                .parkinglotId(request.getParkinglotId())
                .build();

        Reservation saved = reservationRepository.save(reservation);

        return saved.getReservationId();
    }

    @Transactional
    public ReservationDetailResponse getReservationDetail(Long reservationId) {
        Reservation reservation = reservationRepository
                .findByReservationId(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        ReservationDto response = modelMapper.map(reservation, ReservationDto.class);
        return new ReservationDetailResponse(response);
    }

    public List<ReservationListResponse> getReservationList(Long userNo) {
        List<Reservation> reservations = reservationRepository
                .findByUserNoOrderByCreatedAtDesc(userNo);

        /*예외처리*/

        return reservations.stream()
                .map(reservation -> {
                    ReservationDto dto = modelMapper.map(reservation, ReservationDto.class);
                    return new ReservationListResponse(dto);
                })
                .toList();
    }

    @Transactional
    public Long cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(()->new BusinessException(ErrorCode.NOT_FOUND));

        // 환불 처리 및 확인

        reservation.cancel();

        return reservationId;
    }
}

