### Beyond SW 21th 2번째 단위 프로젝트

# 차곡🚘차곡🌾 (주차장 예약 시스템)

차곡차곡은 도시 내 부족한 주차 공간 문제를 해결하기 위해 설계된 **주차장 실시간 예약·조회 시스템**이다.
사용자는 주변 주차장을 검색하고, 요금정보를 확인한 뒤, 예약·결제까지 한 번에 처리할 수 있다.
또한 이용 후 리뷰를 작성해 다른 사용자에게 주차장 품질 정보를 제공한다.

***

## 팀원 정보

| 이름 | GitHub 아이디 | 프로필 링크 |
|------|----------------|--------------|
| 이형욱 | danielhw99 | https://github.com/danielhw99 |
| 이하경 | fijitlwkr | https://github.com/fijitlwkr |
| 정하경 | hathang16 | https://github.com/hathang16 |
| 이상준 | Ongsaem0 | https://github.com/Ongsaem0 |
| 이건우 | guntinue | https://github.com/guntinue |

***

## 주요 기능

### 1. 유저(User)

* 회원가입, 로그인
* 마이페이지(예약 내역, 결제 내역, 작성 리뷰 조회)

### 2. 주차장(Parking Lot)

* 위치 기반 주차장 검색
* 상세 정보 제공

  * 남은 자리 수
  * 요금 구조
  * 운영 시간
* 리뷰 평균 별점 표시

### 3. 예약(Reservation)

* 원하는 날짜/시간 기반 예약
* 예약 취소 기능
* 이용 완료 후 리뷰 작성 가능

### 4. 리뷰(Review)

* 사용자는 특정 주차장 이용 후 리뷰 작성 가능
* 다른 사용자 리뷰 조회 가능

### 5. 결제(Payment)

* 예약금으로 결제 진행

---

## 서비스 필요성

### 도심 주차 공간 부족 문제

국토교통부 자료에 따르면, 수도권 및 주요 도심 지역은 등록 차량 대비 주차 공간이 여전히 부족한 상황이다.
실제 이용자는 목적지 주변에 주차할 수 있는 공간이 있는지 알기 어려워 **시간 낭비**, **불필요한 이동**, **교통 혼잡 증가**가 발생한다.

### 기존 서비스의 한계

현재 제공되는 주차장 정보 서비스는 **예약 기능 부재**, **실시간 잔여 자리 부족**, **리뷰 기반 품질 정보 부족** 등의 문제가 존재한다.
사용자에게 필요한 정보와 실제 경험 기반 신뢰도를 동시에 제공하는 시스템의 수요가 증가하고 있다.

---

## 유사 서비스

### 카카오T 주차

* [https://parking.kakao.com](https://parking.kakao.com)
  주차장 조회·결제 기능을 제공하지만, 일부 지역에 한정되어 있거나, 리뷰 기반 품질 정보가 제한적이라는 한계가 있다.


---

## 핵심 가치

* **시간 절약**: 목적지 주변 주차장을 미리 확인하고 예약 가능
* **신뢰성**: 사용자 리뷰 기반으로 주차장 품질 정보 제공
* **편의성**: 검색 → 예약 → 결제 → 리뷰까지 원스톱 프로세스
* **투명성**: 요금 정보/운영 시간/자리 현황을 명확하게 제공

---

## 기술 스택 (Tech Stack)

### Backend

| 구분 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Build Tool | Gradle |
| Architecture | MSA (Command / Query 분리) |
| Web | Spring Web (REST API) |
| Security | Spring Security (JWT 기반 인증, Header Authentication) |
| Data Access | Spring Data JPA |
| Database | MariaDB / MySQL |
| ORM | Hibernate |
| API Documentation | Swagger (Springdoc OpenAPI) |
| Inter-service Communication | OpenFeign |
| Testing | JUnit 5, Mockito, MockMvc |
| Validation | Spring Validation |
| Logging | SLF4J + Logback |
| Version Control | Git / GitHub |


---

## 산출물

### MSA 구조도
<details>
 <summary>MSA 구조도</summary>
> <img width="800" height="" alt="image" src="./images/MSA.png" />
</details>

---

### ERD
<details>
 <summary>ERD</summary>
> <img width="800" height="" alt="image" src="./images/erd.png" />
</details>

---

### WBS
<details>
> <img width="800" height="" alt="images" src="./images/docs/chagok-wbs.jpg" />
</details>

---

### 요구사항명세서
<details>
> <img width="800" height="" alt="image" src="./images/docs/chagok_requirements.jpg" />
</details>

---

### DDD 설계문서
<details>
 <summary>DDD 문서</summary>
 <img width="800" height="" alt="image" src="./images/DDD/Valet Parkar.jpg" />
 
 <img width="800" height="" alt="image" src="./images/DDD/Valet Parkar (2).jpg" />
 
 <img width="800" height="" alt="image" src="./images/DDD/Valet Parkar (3).jpg" />
 
 <img width="800" height="" alt="image" src="./images/DDD/Valet Parkar (4).jpg" />
 
 <img width="800" height="" alt="image" src="./images/DDD/Valet Parkar (final).jpg" />
</details>

---

### 테이블정의서
<details>
> <img width="800" height="" alt="image" src="./images/docs/chagok-tableDefs.jpg" />
</details>

## 소감문
## 👥 팀원 소감

| 이름 | 담당 역할 | 프로젝트 소감 |
|------|-----------|---------------|
| 이건우 | 결제 |  |
| 이상준 | 주차장 |  |
| 이하경 | 리뷰 |  |
| 이형욱 | 예약 | 이번 프로젝트는 기능 구현보다 개발의 기본기를 다지는 데 초점을 둔 경험이었습니다. 기획 단계에서는 API 구현을 비교적 단순하게 생각했지만, 실제 개발 과정에서 API 동작 구조에 대한 이해 부족으로 많은 어려움을 겪었고, 팀원들과의 소통을 통해 하나씩 배워가며 구현할 수 있었습니다. 예약과 이용 현황 업데이트 기능을 맡았으나, 설계 단계의 논리적 미흡함과 시간 제약으로 일부 기능을 제외하게 되었고, 이를 통해 기획 단계에서의 철저한 논리 정리와 현실적인 범위 설정, 그리고 스스로의 역량을 객관적으로 파악하는 메타인지의 중요성을 깊이 깨닫는 계기가 되었습니다. |
| 정하경 | 회원관리 |  |
