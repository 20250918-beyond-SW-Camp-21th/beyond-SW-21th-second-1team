//package com.valetparker.chagok.parkinglot.repository;
//
//import com.valetparker.chagok.parkinglot.domain.Parkinglot;
//import com.valetparker.chagok.parkinglot.enums.Seouldistrict;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ParkinglotRepository extends JpaRepository<Parkinglot, Long> {
//
//    // 1️⃣ 구별로 주차장 검색 (페이징 포함)
//    // 예: GANGNAM 구에서 페이지 0, 크기 10
//    Page<Parkinglot> findBySeoulDistrict(Seouldistrict seoulDistrict, Pageable pageable);
//
//    // 2️⃣ 구별로 "잔여 자리가 있는" 주차장만 검색
//    // 예: SEOCHO 구에서 usedSpots < totalSpots인 것만
//    @Query("SELECT p FROM Parkinglot p WHERE p.seoulDistrict = :district AND p.usedSpots < p.totalSpots")
//    List<Parkinglot> findAvailableByDistrict(@Param("district") Seouldistrict seoulDistrict);
//
//    // 3️⃣ 평점순으로 정렬 (높은 순서)
//    // 예: NOWON 구에서 평점이 높은 주차장부터
//    @Query("SELECT p FROM Parkinglot p WHERE p.seoulDistrict = :district ORDER BY p.averageRating DESC")
//    List<Parkinglot> findByDistrictOrderByRating(@Param("district") Seouldistrict seoulDistrict);
//}
//
