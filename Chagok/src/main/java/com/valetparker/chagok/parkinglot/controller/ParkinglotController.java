//package com.valetparker.chagok.parkinglot.controller;
//
//import com.valetparker.chagok.parkinglot.dto.response.ParkinglotListResponse;
//import com.valetparker.chagok.parkinglot.dto.response.ParkinglotResponse;
//import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
//import com.valetparker.chagok.parkinglot.service.ParkinglotService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/parkinglots")
//@Tag(name = "🏢 주차장 API", description = "서울 구별 주차장 검색, 조회, 요금 계산")
//public class ParkinglotController {
//
//    private final ParkinglotService parkinglotService;
//
//    // ========== API 1: 구별 주차장 검색 ==========
//    @GetMapping
//    @Operation(
//            summary = "구별 주차장 검색",
//            description = """
//            서울 특정 구의 주차장 목록을 평점순으로 반환합니다.
//            - GANGNAM: 강남구
//            - SEOCHO: 서초구
//            - NOWON: 노원구
//            등등... 모든 서울 25개 구 지원
//            """
//    )
//    public ResponseEntity<ParkinglotListResponse> searchParkinglots(
//            @Parameter(
//                    description = "검색할 서울 구",
//                    example = "GANGNAM",
//                    required = true
//            )
//            @RequestParam Seouldistrict district,
//
//            @Parameter(
//                    description = "페이지 번호 (0부터 시작)",
//                    example = "0"
//            )
//            @RequestParam(defaultValue = "0") int page,
//
//            @Parameter(
//                    description = "페이지당 주차장 개수",
//                    example = "10"
//            )
//            @RequestParam(defaultValue = "10") int size,
//
//            @Parameter(
//                    description = "정렬 방향",
//                    example = "DESC"
//            )
//            @RequestParam(defaultValue = "DESC") Sort.Direction direction
//    ) {
//        // Pageable 객체 생성 (페이지, 크기, 정렬)
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "averageRating"));
//
//        // Service 호출
//        ParkinglotListResponse response = parkinglotService.searchByDistrict(district, pageable);
//
//        // 응답
//        return ResponseEntity.ok(response);
//    }
//
//    // ========== API 2: 주차장 상세 조회 ==========
//    @GetMapping("/{parkinglotId}")
//    @Operation(
//            summary = "주차장 상세 조회",
//            description = "특정 주차장의 모든 정보를 반환합니다"
//    )
//    public ResponseEntity<ParkinglotResponse> getParkinglotDetail(
//            @Parameter(
//                    description = "주차장 ID",
//                    example = "1",
//                    required = true
//            )
//            @PathVariable Long parkinglotId
//    ) {
//        ParkinglotResponse response = parkinglotService.getDetail(parkinglotId);
//        return ResponseEntity.ok(response);
//    }
//
//    // ========== API 3: 요금 계산 ==========
//    @GetMapping("/{parkinglotId}/fee")
//    @Operation(
//            summary = "주차 요금 계산",
//            description = "입력한 주차 시간(분)으로 정확한 요금을 계산합니다"
//    )
//    public ResponseEntity<Integer> calculateFee(
//            @Parameter(
//                    description = "주차장 ID",
//                    example = "1",
//                    required = true
//            )
//            @PathVariable Long parkinglotId,
//
//            @Parameter(
//                    description = "주차 시간 (분 단위)",
//                    example = "90",
//                    required = true
//            )
//            @RequestParam int minutes
//    ) {
//        int fee = parkinglotService.calculateFee(parkinglotId, minutes);
//        return ResponseEntity.ok(fee);
//    }
//
//    // ========== API 4: 평점순 주차장 ==========
//    @GetMapping("/top-rated/{district}")
//    @Operation(
//            summary = "평점순 주차장 조회",
//            description = "특정 구에서 평점이 높은 순서대로 주차장 반환"
//    )
//    public ResponseEntity<List<ParkinglotResponse>> getTopRated(
//            @Parameter(
//                    description = "서울 구",
//                    example = "GANGNAM",
//                    required = true
//            )
//            @PathVariable Seouldistrict district
//    ) {
//        List<ParkinglotResponse> response = parkinglotService.getTopRatedByDistrict(district);
//        return ResponseEntity.ok(response);
//    }
//
//    // ========== 예외 처리 ==========
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
//        return ResponseEntity.badRequest().body("❌ 오류: " + e.getMessage());
//    }
//}
