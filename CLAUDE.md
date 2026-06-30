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
| ka10099 | 종목정보 리스트 | fn_ka10099 | ✅ |
| ka10001 | 주식기본정보요청 | fn_ka10001 | ✅ |
| ka10003 | 체결정보요청 | fn_ka10003 | ✅ |
| ka10023 | 거래량급증요청 | fn_ka10023 | ✅ |
| ka10027 | 전일대비등락률상위요청 | fn_ka10027 | ✅ |
| ka10030 | 당일거래량상위요청 | fn_ka10030 | ✅ |
| ka10032 | 거래대금상위요청 | fn_ka10032 | ✅ |
| ka10095 | 관심종목정보요청 | fn_ka10095 | ✅ |
| ka10100 | 종목정보조회 | fn_ka10100 | ✅ |
| ka10101 | 업종코드 리스트 | fn_ka10101 | ✅ |

### ETF 정보 조회 (KiwoomETFInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka40002 | ETF종목정보요청 | fn_ka40002 | ✅ |
| ka40003 | ETF일별추이요청 | fn_ka40003 | ✅ |
| ka40004 | ETF전체시세요청 | fn_ka40004 | ✅ |
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
1. ~~`KiwoomSTKInfoService`: `fn_ka00199` → `fn_ka10099`로 리네임 필요~~ ✅ 완료
2. ~~`KiwoomSTKInfoService`: `fn_ka00100` → `fn_ka10100`으로 리네임 필요~~ ✅ 완료
3. ~~`TokenService`: 변수명 `mockhost` → `host`로 리네임 필요 (realhost 값 사용중)~~ ✅ 완료
4. ~~`KiwoomETFInfoService`: 불필요한 import 제거 (`org.springframework.data.repository.query.Param`)~~ ✅ 완료
5. ~~`KiwoomETFInfoService.fn_ka40004`: 파라미터 하드코딩 → 파라미터화 검토~~ ✅ 완료

## API 공통 스펙
- Base URL: `${kiwoom.realhost}` (application.properties에서 설정)
- 모든 요청: POST, Content-Type: application/json;charset=UTF-8
- 공통 헤더: `authorization`, `api-id`, `cont-yn`, `next-key`
- 연속조회: 응답 헤더의 `cont-yn=Y`이면 `next-key` 값으로 다음 페이지 요청

---

## 작업 체크리스트

### 지금 당장 할 수 있는 작업
- [o] `.gitignore` 설정 — `application.properties`, `kiwoom_api.yml`, `.idea/` 제외
- [ ] `application.properties` 환경변수화 — 키값을 `${ENV_VAR}` 형태로 분리
- [o] Gradle 빌드 확인 — `./gradlew build` 로 JAR 생성되는지 확인
- [o] 함수명 오타 수정 — `fn_ka00199` → `fn_ka10099`, `fn_ka00100` → `fn_ka10100`
- [o] 변수명 오타 수정 — `TokenService`의 `mockhost` → `host`
- [o] 불필요한 import 제거 — `KiwoomETFInfoService`의 `Param` import
- [o] `fn_ka40004` 파라미터 하드코딩 → 파라미터화
- [o] 순위정보 TR 추가 — ka10023(거래량급증), ka10027(등락률상위), ka10030(거래량상위), ka10032(거래대금상위)

### EC2 배포를 위해 해야 하는 작업
- [o] EC2 인스턴스 생성 (Ubuntu 26.04 LTS)
- [o] EC2에 Java 설치 (OpenJDK 21)
- [o] 보안 그룹 설정 — 8080 포트 인바운드 오픈
- [o] JAR 파일 EC2로 업로드 (`scp` 사용, WSL에서 실행)
- [o] EC2에 `kiwoom_api.yml` 직접 생성 (외부 설정 파일 방식)
- [o] 키움 API IP 화이트리스트에 EC2 IP 등록 (13.210.159.103)
- [o] EC2에서 nohup 백그라운드 실행 확인
- [o] API 정상 동작 확인 — ka10001 삼성전자 기본정보 조회 성공
- [ ] systemd 서비스 등록 — EC2 재시작 시 자동 실행
- [ ] AWS CloudWatch 연동 — 로그 모니터링
- [ ] nginx 리버스 프록시 설정 — 80포트로 8080 포워딩
- [ ] GitHub Actions CI/CD 구성

## 보안 TODO

### 지금 당장
- [ ] AWS 보안 그룹 8080 포트를 내 IP로만 좁히기 — `0.0.0.0/0` → `My IP` (매수/매도 엔드포인트가 인증 없이 열려있으므로 즉시 처리)
- [ ] 블로그 발행 전 EC2 IP 마스킹 — `13.210.159.103` → `13.x.x.x` 또는 `YOUR_EC2_IP`
- [ ] `logging.level.root=DEBUG` → `WARN` 으로 변경 후 재배포 — DEBUG 로그에 `authorization: Bearer 토큰값` 이 그대로 app.log에 기록되고 있음

### 단기 (CI/CD 작업 전후)
- [ ] 매수/매도 엔드포인트 API Key 인증 추가 — 헤더 `X-API-KEY` 없으면 403 반환 (인터셉터로 구현)
- [ ] Swagger UI 접근 제한 — 보안 그룹을 내 IP로 좁히면 같이 해결되나, 나중에 공개 시 별도 처리 필요

### 중기 (로그인 페이지 작업 시)
- [ ] Spring Security + JWT 로그인 구현
- [ ] 경로별 권한 분리 — `/api/stock/**` 인증 불필요 / `/api/trade/**` 로그인 필수
- [ ] HTTPS 적용 — nginx 리버스 프록시 설정 시 같이 처리

> **주의:** 키움 IP 화이트리스트는 "다른 서버가 키움에 직접 요청하는 것"만 막음.
> "아무나 내 앱을 거쳐서 키움에 요청하는 것"은 앱 레벨 인증으로 별도로 막아야 함.

---

## EC2 서버 정보
- IP: 13.210.159.103
- OS: Ubuntu 26.04 LTS
- Java: OpenJDK 21
- 포트: 8080
- JAR: /home/ubuntu/core-0.0.1-SNAPSHOT.jar
- 설정: /home/ubuntu/kiwoom_api.yml
- 로그: /home/ubuntu/app.log
- SSH 키: ~/.ssh/stockFor.pem (WSL 경로)
- 실행 방식: nohup 백그라운드

### SSH 접속
```bash
ssh -i ~/.ssh/stockFor.pem ubuntu@13.210.159.103
```

### 재배포 순서
```
1. 로컬: ./gradlew bootJar (core/ 디렉토리에서)
2. 로컬: scp로 JAR 업로드
3. EC2: 기존 프로세스 kill 후 nohup 재실행
```
