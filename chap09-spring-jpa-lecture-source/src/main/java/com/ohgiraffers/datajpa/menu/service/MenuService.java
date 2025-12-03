package com.ohgiraffers.datajpa.menu.service;

import com.ohgiraffers.datajpa.menu.dto.MenuDTO;
import com.ohgiraffers.datajpa.menu.entity.Menu;
import com.ohgiraffers.datajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    /* DTO와 Entity의 분리
     * DTO(Data Transfer Object)는 프레젠테이션 계층과의 데이터 교환을 위한 객체로,
     * 도메인 로직을 담고 있는 Entity와 분리하여 사용함으로써 보안, 성능, 유지보수 측면의 이점을 얻을 수 있다.
     * 1. 보안 : 민감 데이터/과도 데이터 노출 방지, 과도한 바인딩 공격 방지
     * 2. 성능 : 데이터 전송 최적화, 지연 로딩 문제 완화, 쿼리 최적화
     * 3. 유지 보수 : 명확한 계층 분리(비즈니스 로직 수정이나 도메인 모델 변경이 클라이언트와의 API 계약에 영향X)
     *               도메인 로직의 캡슐화(Entity 관련 비즈니스 로직은 외부에 노출 X, 클라이언트는 DTO만 이용)
     * */

    /* DTO - Entity의 변환 방법
     * 1. 수동 매핑 메서드
     * 2. Spring BeanUtils.copyProperties()
     * 3. ModelMapper 라이브러리
     * 4. MapStruct 라이브러리
     * 5. Spring Data JPA Projection */


    public MenuDTO findMenuByMenuCode(int menuCode) {
        Menu menu = menuRepository.findById(menuCode).orElseThrow(IllegalArgumentException::new);  // entity->dto
        return  modelMapper.map(menu, MenuDTO.class);
    }
}