package com.valetparker.reparkingservice.query.service;

import com.valetparker.reparkingservice.common.dto.Pagination;
import com.valetparker.reparkingservice.common.dto.ParkinglotDto;
import com.valetparker.reparkingservice.common.entity.Parkinglot;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import com.valetparker.reparkingservice.common.exception.BusinessException;
import com.valetparker.reparkingservice.common.exception.ErrorCode;
import com.valetparker.reparkingservice.query.dto.ParkinglotDetailResponse;
import com.valetparker.reparkingservice.query.dto.ParkinglotListResponse;
import com.valetparker.reparkingservice.query.dto.ParkinglotSearchRequest;
import com.valetparker.reparkingservice.query.enums.ParkinglotSortType;
import com.valetparker.reparkingservice.query.repository.ParkinglotQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkinglotQueryService {

    private final ParkinglotQueryRepository parkinglotQueryRepository;

    // 주차장 상세조회
    @Transactional(readOnly = true)
    public ParkinglotDetailResponse getOneParkinglot(Long parkinglotId) {
        Parkinglot parkinglot = parkinglotQueryRepository.findById(parkinglotId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        ParkinglotDto parkinglotDto = ParkinglotDto.from(parkinglot);

        return ParkinglotDetailResponse.builder()
                .parkinglotDto(parkinglotDto)
                .build();
    }

    // 주차장 전체 조회 (이름순/평균별점높은순/평균별점낮은순/남은자리많은순) + (서울시구필터링)
    @Transactional(readOnly = true)
    public ParkinglotListResponse getParkinglots(ParkinglotSearchRequest request) {
        log.info("ParkinglotSearchRequest: page={}, size={}, sort={}, district={}",
                request.getPage(), request.getSize(), request.getSort(), request.getSeoulDistrict());

        int page = request.getPage();  // 1-based
        int size = request.getSize();
        ParkinglotSortType sortType = request.getSort();
        SeoulDistrict seoulDistrict = request.getSeoulDistrict();

        Sort sort = switch (sortType) {
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "name");
            case AVGRATING_DESC -> Sort.by(Sort.Direction.DESC, "avgRating");
            case AVGRATING_ASC -> Sort.by(Sort.Direction.ASC, "avgRating");
            case USEDSPOTS_ASC -> Sort.by(Sort.Direction.ASC, "usedSpots");
        };

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Parkinglot> parkinglotPage =
                (seoulDistrict == null)
                        ? parkinglotQueryRepository.findAll(pageable)
                        : parkinglotQueryRepository.findAllBySeoulDistrict(seoulDistrict, pageable);

        List<ParkinglotDto> parkinglotDtoList = parkinglotPage.getContent().stream()
                .map(ParkinglotDto::from)
                .toList();

        Pagination pagination = Pagination.builder()
                .currentPage(page)
                .totalPages(parkinglotPage.getTotalPages())
                .totalItems(parkinglotPage.getTotalElements())
                .build();

        return ParkinglotListResponse.builder()
                .parkinglotDtoList(parkinglotDtoList)
                .pagination(pagination)
                .sortType(sortType)
                .build();
    }
}
