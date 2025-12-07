//package com.valetparker.chagok.parkinglot.dto.response;
//
//import com.valetparker.chagok.parkinglot.domain.Parkinglot;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@Builder
//@AllArgsConstructor
//public class ParkinglotResponse {
//
//    // 응답에 포함될 필드들
//    private Long parkinglotId;           //     주차장 ID
//    private String name;                 //     주차장 이름
//    private String address;              //     주소
//    private String seoulDistrictName;    //     구 이름
//    private int totalSpots;              //     총 자리 수
//    private int remainingSpots;          //     남은 자리 수
//    private double averageRating;        //     평점
//    private int baseFee;                 //     기본 요금
//    private int baseTime;                //     기본 시간(분)
//    private int unitFee;                 //     추가 요금
//    private int unitTime;                //     추가 시간 단위 (분)
//
//    public static ParkinglotResponse from(Parkinglot parkinglot) {
//        return ParkinglotResponse.builder()
//                .parkinglotId(parkinglot.getParkinglotId())
//                .name(parkinglot.getName())
//                .address(parkinglot.getAddress())
//                .seoulDistrictName(parkinglot.getSeoulDistrict().getKoreanName())  // 한글명
//                .totalSpots(parkinglot.getTotalSpots())
//                .remainingSpots(parkinglot.getRemainingSpots())  // 메서드 호출
//                .averageRating(parkinglot.getAverageRating())
//                .baseFee(parkinglot.getBaseFee())
//                .baseTime(parkinglot.getBaseTime())
//                .unitFee(parkinglot.getUnitFee())
//                .unitTime(parkinglot.getUnitTime())
//                .build();
//    }
//}
