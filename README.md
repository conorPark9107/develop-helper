# AlbionGG 


제가 좋아하는 게임인 Albion Online의 데이터를 활용한 웹 서비스입니다.

Albion Online에서 생성되는 전투 및 경제 데이터를 수집·가공하여
유저의 의사결정에 도움이 되는 정보를 제공합니다.

🔗 배포 링크: https://albiongg.com

<br><br>

## 주요 기능들

### 🔍 데이터 조회
- 길드 / 연합별 전투 내역 검색
- 플레이어 정보 검색(킬 명성, 몹 명성, 채집 명성 ..등)
- 플레이어 최근 킬 / 데스 조회

### 📊 데이터 가공 및 분석
- PvP 로그 데이터 수집 및 가공
- Stream API를 활용한 필터링 및 변환 처리

### 🛒 경제 시스템
- 마을별 시장 아이템 가격 조회
- 제련 / 요리 / 도살 / 방앗간 계산기 제공

### 📝 커뮤니티
- 게시판 작성
- 티어리스트 작성
- 댓글 기능

### 🛠️ 관리자 기능
- 관리자 로그인 (Spring Security)
- 문의 답변 관리
- 날짜별 방문자 수 통계
- 게시판 카테고리 관리

<br><br>

## 🛠️ Tech Stack

### Backend
- Java
- Spring Boot
- Spring Data JPA
- MariaDB
- WebClient (외부 API 통신)
- Spring Security

### Frontend
- HTML
- CSS
- JavaScript
- jQuery

### Infra / DevOps
- Docker
- Docker Hub
- 컨테이너 기반 배포

<br><br>

### 1. 대용량 PvP 데이터 처리
- 주기적으로 외부 API에서 데이터를 수집
- 불필요한 데이터 필터링 및 가공

### 2. 성능 최적화
- 캐싱 적용으로 반복 요청 최소화

### 3. 협업 및 코드 품질
- 일관된 코드 스타일 유지
- 재사용 가능한 구조 설계

### 시스템 아키텍쳐
```plaintext
Client 
  → Controller 
    → Service 
      → WebClient → Albion Online API
      → Repository → MariaDB
    → Service 
  → Controller 
→ Client
```

- WebClient를 활용한 외부 API 통신
- Service 레이어에서 데이터 가공 후 클라이언트에 제공

<br><br>

## 🔥 핵심 구현 내용
1. 외부 API 데이터 처리
- WebClient를 통해 PvP 및 플레이어 데이터 수집
- Stream API를 활용하여 필요한 데이터만 필터링 및 가공
- 불필요한 데이터 제거로 응답 효율성 개선

<br>

2. HTTP 응답 캐싱을 통한 성능 개선
- 반복 요청이 많은 API 응답에 캐싱하여 응답 속도 개선
- 외부 API 호출 횟수 최소화

<br>

3. 인증 및 관리자 시스템 구축
- Spring Security 기반 로그인 구현
- 관리자 권한 분리 및 접근 제어

<br>

4. 데이터 기반 기능 설계
- PvP 로그 → 통계 데이터로 가공
- 시장 데이터 → 가격 조회 시스템 구축
- 제련, 요리, 도살, 방앗간 계산기 → 게임 내 자원별 마켓데이터를 활용한 가격과 최적화 지원

<br>

⚠️ 트러블슈팅
1. 외부 API 의존성으로 인한 응답 지연


문제
- API 호출 증가 시 응답 속도 저하

해결
- HTTP 응답 캐싱 적용
- 요청별 캐시 타이머 설정으로 호출 최소화


<br><br>

2. 인증 및 권한 분리 문제


문제
- 관리자와 일반 사용자 기능 분리 필요

해결
- Spring Security 기반 인증/인가 처리
- URL 패턴별 접근 권한 설정

<br><br>

## 📁 프로젝트 구조
```plaintext
com.albionhelper.helper
├── admin            # 관리자 기능 (문의, 통계, 카테고리 관리)
├── api              # 외부 API 통신 관련 로직
├── battleBoard      # 길드 / 연합 전투 데이터 처리
├── board            # 게시판 기능
├── calculator       # 각종 계산기 (제련, 요리, 도살장 등)
├── config           # 설정 파일 (Security, WebClient 등)
├── domain           # 엔티티 및 도메인 모델
├── enums            # 공통 Enum 정의
├── filter           # 요청 필터 처리
├── interceptors     # 인터셉터 (로깅, 인증 등)
├── itemInfo         # 아이템 정보 관련 기능
├── killApiScheduler # PvP 데이터 수집 스케줄러
├── logger           # 로그 처리
├── market           # 시장 가격 조회 기능
├── playerKillBoard  # 플레이어 킬/데스 데이터 처리
├── securityUser     # 사용자 인증 및 권한 관리
├── tierList         # 티어리스트 기능
├── util             # 공통 유틸

resources/
├── static           # 정적 파일 (CSS, JS, 이미지)
├── templates        # 서버 렌더링 HTML (Thymeleaf)
```

<br><br>

## 💡 설계 특징
- 기능 단위 패키지 구조를 적용
- Controller / Service / Repository 계층 구조 유지
- 외부 API, 스케줄러, 인증, 인터셉터 등을 독립적인 모듈로 구성
- 공통 로직은 util 패키지로 분리하여 재사용성 향상

<br>

#### ✔️ 설계 의도
기능 중심 구조로 설계하여 유지보수성과 확장성을 고려했습니다.
