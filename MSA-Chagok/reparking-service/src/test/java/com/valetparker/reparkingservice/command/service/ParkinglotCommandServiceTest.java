package com.valetparker.reparkingservice.command.service;

import com.valetparker.reparkingservice.command.dto.*;
import com.valetparker.reparkingservice.command.repository.ParkinglotCommandRepository;
import com.valetparker.reparkingservice.common.entity.Parkinglot;
import com.valetparker.reparkingservice.common.enums.SeoulDistrict;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ParkinglotCommandServiceTest {

    @InjectMocks
    private ParkinglotCommandService parkinglotCommandService;

    @Mock
    private ParkinglotCommandRepository parkinglotCommandRepository;

    @Test
    @DisplayName("성공: 주차장 정상 등록")
    void createParkinglot_success() {
        // given
        ParkinglotCreateRequest request = new ParkinglotCreateRequest(
                "테스트 주차장", "서울시 강남구", SeoulDistrict.GANGNAM, 50, 1000, 500
        );

        // Mocking: 저장 후 ID가 1인 객체 반환
        Parkinglot mockSavedParkinglot = new Parkinglot(
                1L, request.getParkingName(), request.getAddress(),
                request.getSeoulDistrict(), request.getTotalSpots(),
                request.getBaseFee(), request.getUnitFee(), true
        );
        given(parkinglotCommandRepository.save(any(Parkinglot.class))).willReturn(mockSavedParkinglot);

        // when
        ParkinglotCommandResponse response = parkinglotCommandService.createParkinglot(request);

        // then
        assertThat(response.getParkingName()).isEqualTo("테스트 주차장");
        verify(parkinglotCommandRepository).save(any(Parkinglot.class));
    }

    @Test
    @DisplayName("성공: 주차장 정보 수정")
    void updateParkinglot_success() {
        // given
        Long parkinglotId = 1L;
        ParkinglotUpdateRequest updateRequest = new ParkinglotUpdateRequest(
                "수정된 주차장", "서울시 서초구", SeoulDistrict.SEOCHO, 200, 3000, 1000
        );

        Parkinglot existingParkinglot = new Parkinglot(parkinglotId, "원래 주차장", "서울시 강남구", SeoulDistrict.GANGNAM, 100, 2000, 500, true
        );
        given(parkinglotCommandRepository.findById(parkinglotId)).willReturn(Optional.of(existingParkinglot));

        // when
        ParkinglotCommandResponse response = parkinglotCommandService.updateParkinglot(parkinglotId, updateRequest);

        // then
        assertThat(response.getParkingName()).isEqualTo("수정된 주차장");
        assertThat(response.getBaseFee()).isEqualTo(3000);
    }

    // 실패 케이스 (검증 로직이 있다고 가정)
    @Test
    @DisplayName("실패: 주차 요금이 음수인 경우 예외 발생")
    void createParkinglot_fail_negative_fee() {
        // given
        ParkinglotCreateRequest invalidRequest = new ParkinglotCreateRequest(
                "공짜 주차장", "서울시", SeoulDistrict.SONGPA, 100, -1000, 500
        );

        // when & then
        // (실제 Service 코드에 if (fee < 0) throw ... 로직이 있어야 통과)
        assertThatThrownBy(() -> parkinglotCommandService.createParkinglot(invalidRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
