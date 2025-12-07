//package com.valetparker.chagok.parkinglot.service;
//
//import com.valetparker.chagok.parkinglot.domain.Parkinglot;
//import com.valetparker.chagok.parkinglot.dto.response.ParkinglotListResponse;
//import com.valetparker.chagok.parkinglot.dto.response.ParkinglotResponse;
//import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
//import com.valetparker.chagok.parkinglot.repository.ParkinglotRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)  // 조회만 하므로 읽기 전용
//public class ParkinglotService {
//
//    private final ParkinglotRepository parkinglotRepository;
//
//    // 기능 1: 구별 주차장 검색 (페이징)
//    public ParkinglotListResponse searchByDistrict(Seouldistrict district, Pageable pageable) {
//        // DB에서 조회
//        Page<Parkinglot> page = parkinglotRepository.findBySeoulDistrict(district, pageable);
//
//        // 응답 DTO로 변환해서 반환
//        return ParkinglotListResponse.builder()
//                .parkinglots(
//                        page.getContent()          // 현재 페이지 데이터
//                                .stream()
//                                .map(ParkinglotResponse::from)  // 각각을 DTO로 변환
//                                .toList()
//                )
//                .currentPage(page.getNumber())    // 0, 1, 2...
//                .totalPages(page.getTotalPages()) // 몇 페이지인지
//                .totalElements(page.getTotalElements())  // 전체 데이터 개수
//                .size(page.getSize())             // 페이지당 크기
//                .build();
//    }
//
//    // 기능 2: 주차장 상세 조회
//    public ParkinglotResponse getDetail(Long parkinglotId) {
//        // ID로 찾기
//        Parkinglot parkinglot = parkinglotRepository.findById(parkinglotId)
//                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다"));
//
//        // DTO로 변환
//        return ParkinglotResponse.from(parkinglot);
//    }
//
//    // 기능 3: 요금 계산
//    public int calculateFee(Long parkinglotId, int minutes) {
//        // 주차장 찾기
//        Parkinglot parkinglot = parkinglotRepository.findById(parkinglotId)
//                .orElseThrow(() -> new RuntimeException("주차장을 찾을 수 없습니다"));
//
//        // 기본 시간(30분) 이내면 기본 요금만
//        if (minutes <= parkinglot.getBaseTime()) {
//            return parkinglot.getBaseFee();
//        }
//
//        // 초과분 계산
//        int extraMinutes = minutes - parkinglot.getBaseTime();
//        // 10분 단위로 올림
//        int extraUnits = (extraMinutes + parkinglot.getUnitTime() - 1) / parkinglot.getUnitTime();
//
//        // 기본 요금 + (추가 시간 단위 수 × 추가 요금)
//        return parkinglot.getBaseFee() + (extraUnits * parkinglot.getUnitFee());
//    }
//
//    // 기능 4: 평점순 조회
//    public List<ParkinglotResponse> getTopRatedByDistrict(Seouldistrict district) {
//        return parkinglotRepository.findByDistrictOrderByRating(district)
//                .stream()
//                .map(ParkinglotResponse::from)
//                .toList();
//    }
//}
