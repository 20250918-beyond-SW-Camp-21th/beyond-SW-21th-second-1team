package paymentservice.reservation.controller;

import com.valetparker.chagok.common.dto.ApiResponse;
import com.valetparker.chagok.reservation.dto.request.ReservationCreateRequest;
import com.valetparker.chagok.reservation.dto.response.ReservationDetailResponse;
import com.valetparker.chagok.reservation.dto.response.ReservationListResponse;
import com.valetparker.chagok.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
//uid pid
    @PostMapping("/regist")
    public ResponseEntity<ApiResponse<Long>> registerReservation(
            @RequestBody ReservationCreateRequest request
    ) {
        Long response = reservationService.createReservation(request);
        return ResponseEntity
                .ok(ApiResponse.success(response));
    }

    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<ReservationDetailResponse>> getReservationDetail(
            @RequestParam Long reservationId
    ) {
        ReservationDetailResponse reservationDetailResponse = reservationService.getReservationDetail(reservationId);
        return ResponseEntity.ok(ApiResponse.success(reservationDetailResponse));
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ReservationListResponse>>> getReservationHistory(
            @RequestParam Long userNo
    ) {
        List<ReservationListResponse> response = reservationService.getReservationList(userNo);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> cancelReservation(
            @RequestParam Long reservationId
    ) {
      Long isCanceled = reservationService.cancelReservation(reservationId);
      return ResponseEntity.ok(ApiResponse.success(isCanceled));
    }
}
