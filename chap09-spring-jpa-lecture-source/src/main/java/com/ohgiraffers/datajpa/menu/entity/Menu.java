package com.ohgiraffers.datajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
/* @Table : 클래스명과 테이블명이 동일할 경우 생략 가능 */
@Table(name = "tbl_menu")
@Getter
/* @Setter 지양
 * 객체를 언제든지 변경할 수 있는 상태가 되어서 객체의 안전성이 보장받기 힘듦
 * 값 변경이 필요한 경우 해당 기능을 수행하는 메서드를 생성하여 사용
 * */
/* AccessLevel.PROTECTED : 기본 생성자의 접근 제한을 통해 부문별한 객체 생성 지양 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/* @AllArgsConstructor 지양
 * 인스턴스 멤버의 선언 순서에 영향을 받기 때문에
 * 변수의 순서를 바꾸면 생성자의 입력 값 순서도 바뀌게 되어
 * 검출되지 않는 치명적인 오류를 발생 시킬 수 있음
 * */
/* @ToString : 사용 시 연관관계 매핑 필드는 제거 */
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;
    private String menuName;
    private int menuPrice;
    private int categoryCode;
    private String orderableStatus;

}