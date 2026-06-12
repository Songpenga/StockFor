# StockFor - 키움증권 REST API 연동 프로젝트

## 프로젝트 목적
키움증권 REST API를 Spring Boot로 감싸서, 국내 주식 정보 조회 및 주문(매수/매도/정정/취소)을 처리하는 백엔드 서버를 만드는 프로젝트.

## 기술 스택
- Java / Spring Boot
- Gradle
- 키움증권 REST API (https://api.kiwoom.com)
- 모의투자 도메인: https://mockapi.kiwoom.com

## 프로젝트 구조 (core 모듈)

```
core/src/main/java/hello/coreStock/
├── Controller/
│   ├── KiwoomController.java         # 공통
│   ├── KiwoomETFController.java      # ETF 조회
│   ├── KiwoomIndsController.java     # 업종 조회
│   ├── KiwoomStockController.java    # 주식 정보 조회
│   ├── KiwoomTokenController.java    # 토큰 관리
│   └── KiwoomTradingController.java  # 주문 (매수/매도/정정/취소)
├── Service/
│   ├── KiwoomApiService.java         # 공통 HTTP 호출 (부모 클래스)
│   ├── TokenService.java             # 토큰 발급/갱신 (au10001)
│   ├── KiwoomSTKInfoService.java     # 국내주식 정보조회 TR
│   ├── KiwoomETFInfoService.java     # ETF 정보조회 TR
│   ├── KiwoomIndsInfoService.java    # 업종 정보조회 TR
│   └── KiwoomTradingService.java     # 주문 TR (kt10000~kt10003)
├── Dto/
│   ├── sellNBuyOrderRequestDto.java  # 매수/매도 요청 DTO
│   ├── editOrderRequestDto.java      # 정정 요청 DTO
│   └── cancelOrderRequestDto.java    # 취소 요청 DTO
└── RestTemplateConfig.java
```

## 현재 구현된 API (키움 TR 기준)

### 토큰
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| au10001 | 접근토큰 발급 | TokenService.getValidToken() | ✅ |

### 주식 정보 조회 (KiwoomSTKInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka00198 | 실시간종목조회순위 | fn_ka00198 | ✅ |
| ka10099 | 종목정보 리스트 | fn_ka00199 ⚠️함수명불일치 | ✅ |
| ka10001 | 주식기본정보요청 | fn_ka10001 | ✅ |
| ka10003 | 체결정보요청 | fn_ka10003 | ✅ |
| ka10095 | 관심종목정보요청 | fn_ka10095 | ✅ |
| ka10100 | 종목정보조회 | fn_ka00100 ⚠️함수명불일치 | ✅ |
| ka10101 | 업종코드 리스트 | fn_ka10101 | ✅ |

### ETF 정보 조회 (KiwoomETFInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka40002 | ETF종목정보요청 | fn_ka40002 | ✅ |
| ka40003 | ETF일별추이요청 | fn_ka40003 | ✅ |
| ka40004 | ETF전체시세요청 | fn_ka40004 (파라미터 하드코딩) | ⚠️ |
| ka40006 | ETF시간대별추이요청 | fn_ka40006 | ✅ |
| ka40007 | ETF시간대별체결요청 | fn_ka40007 | ✅ |
| ka40008 | ETF일자별체결요청 | fn_ka40008 | ✅ |
| ka40009 | ETF시간대별체결요청 | fn_ka40009 | ✅ |
| ka40010 | ETF시간대별추이요청 | fn_ka40010 | ✅ |

### 업종 정보 조회 (KiwoomIndsInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka20001 | 업종현재가요청 | fn_ka20001 | ✅ |
| ka20002 | 업종별주가요청 | fn_ka20002 | ✅ |
| ka20009 | 업종현재가일별요청 | fn_ka20009 | ✅ |

### 주문 (KiwoomTradingService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| kt10000 | 주식 매수주문 | fn_kt10000 | ✅ |
| kt10001 | 주식 매도주문 | fn_kt10001 | ✅ |
| kt10002 | 주식 정정주문 | fn_kt10002 | ✅ |
| kt10003 | 주식 취소주문 | fn_kt10003 | ✅ |

## 알려진 문제 (TODO)
1. `KiwoomSTKInfoService`: `fn_ka00199` → `fn_ka10099`로 리네임 필요
2. `KiwoomSTKInfoService`: `fn_ka00100` → `fn_ka10100`으로 리네임 필요
3. `TokenService`: 변수명 `mockhost` → `host`로 리네임 필요 (realhost 값 사용중)
4. `KiwoomETFInfoService`: 불필요한 import 제거 (`org.springframework.data.repository.query.Param`)
5. `KiwoomETFInfoService.fn_ka40004`: 파라미터 하드코딩 → 파라미터화 검토
6. 미구현 TR: ka10002(주식거래원요청), kt10006~kt10009(신용주문) 등

## API 공통 스펙
- Base URL: `${kiwoom.realhost}` (application.properties에서 설정)
- 모든 요청: POST, Content-Type: application/json;charset=UTF-8
- 공통 헤더: `authorization`, `api-id`, `cont-yn`, `next-key`
- 연속조회: 응답 헤더의 `cont-yn=Y`이면 `next-key` 값으로 다음 페이지 요청

---

## 작업 체크리스트

### 지금 당장 할 수 있는 작업
- [ ] `.gitignore` 설정 — `application.properties`, `kiwoom_api.yml`, `.idea/` 제외
- [ ] `application.properties` 환경변수화 — 키값을 `${ENV_VAR}` 형태로 분리
- [ ] Gradle 빌드 확인 — `./gradlew build` 로 JAR 생성되는지 확인
- [ ] 함수명 오타 수정 — `fn_ka00199` → `fn_ka10099`, `fn_ka00100` → `fn_ka10100`
- [ ] 변수명 오타 수정 — `TokenService`의 `mockhost` → `host`
- [ ] 불필요한 import 제거 — `KiwoomETFInfoService`의 `Param` import
- [ ] `fn_ka40004` 파라미터 하드코딩 → 파라미터화

### EC2 배포를 위해 해야 하는 작업
- [ ] EC2 인스턴스 생성 (Amazon Linux 2 또는 Ubuntu 추천)
- [ ] EC2에 Java 17 설치
- [ ] 보안 그룹 설정 — Spring Boot 기본 포트 8080 열기
- [ ] JAR 파일 EC2로 업로드 (`scp` 사용)
- [ ] EC2에 `application.properties` 또는 환경변수 직접 설정
- [ ] EC2에서 JAR 실행 확인
- [ ] (선택) systemd 서비스 등록 — 서버 재시작 시 자동 실행
