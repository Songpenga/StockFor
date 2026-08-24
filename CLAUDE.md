# StockFor - 키움증권 REST API 연동 프로젝트

## 프로젝트 목적
키움증권 REST API를 Spring Boot로 감싸서, 국내 주식 정보 조회 및 주문(매수/매도/정정/취소)을 처리하는 백엔드 서버를 만드는 프로젝트.

## 기술 스택
- Java / Spring Boot
- Gradle
- 키움증권 REST API (https://api.kiwoom.com)
- 모의투자 도메인: https://mockapi.kiwoom.com

## 프로젝트 구조

```
StockFor/
├── core/
│   ├── Dockerfile                        # 로컬 빌드 JAR 복사 방식 (eclipse-temurin:21-jre)
│   └── src/main/java/hello/coreStock/
├── nginx/
│   └── default.conf                      # Docker용 nginx 설정 (proxy → springboot:8080)
└── docker-compose.yml                    # postgres + springboot + nginx 컨테이너 구성
```

## 프로젝트 구조 (core 모듈)

```
core/src/main/java/hello/coreStock/
├── Controller/
│   ├── KiwoomAccountController.java  # 계좌 자산현황 조회 (X-API-KEY 인증 필요)
│   ├── KiwoomChartController.java    # 캔들/거래량 차트 조회 (틱/분/일/주/월/년봉)
│   ├── KiwoomController.java         # 공통
│   ├── KiwoomETFController.java      # ETF 조회
│   ├── KiwoomIndsController.java     # 업종 조회
│   ├── KiwoomStockController.java    # 주식 정보 조회
│   ├── KiwoomThemeController.java    # 테마 조회
│   ├── KiwoomTokenController.java    # 토큰 관리
│   └── KiwoomTradingController.java  # 주문 (매수/매도/정정/취소)
├── Service/
│   ├── KiwoomApiService.java         # 공통 HTTP 호출 (부모 클래스)
│   ├── TokenService.java             # 토큰 발급/갱신 (au10001)
│   ├── KiwoomSTKInfoService.java     # 국내주식 정보조회 TR
│   ├── KiwoomETFInfoService.java     # ETF 정보조회 TR
│   ├── KiwoomIndsInfoService.java    # 업종 정보조회 TR
│   ├── KiwoomThemeInfoService.java   # 테마 정보조회 TR (ka90001, ka90002)
│   ├── KiwoomChartInfoService.java   # 차트 정보조회 TR (ka10079~ka10094)
│   ├── KiwoomAccountInfoService.java # 계좌 정보조회 TR (kt00004)
│   └── KiwoomTradingService.java     # 주문 TR (kt10000~kt10003)
├── Dto/
│   ├── sellNBuyOrderRequestDto.java  # 매수/매도 요청 DTO
│   ├── editOrderRequestDto.java      # 정정 요청 DTO
│   ├── cancelOrderRequestDto.java    # 취소 요청 DTO
│   ├── RankItemResponse.java         # 거래대금상위(ka10032)/거래량상위(ka10030) 공용 응답 DTO (record)
│   ├── SearchRankItemResponse.java   # 실시간종목조회순위(ka00198) 응답 DTO (record)
│   ├── ThemeGroupResponse.java       # 테마그룹별요청(ka90001) 응답 DTO (record)
│   ├── ThemeConstituentResponse.java # 테마구성종목요청(ka90002) 응답 DTO (record)
│   ├── IndustryRankResponse.java     # 전업종지수요청(ka20003) 응답 DTO (record)
│   ├── CandleResponse.java           # 일/주/월/년봉 캔들 응답 DTO (record)
│   ├── IntradayCandleResponse.java   # 틱/분봉 캔들 응답 DTO (record)
│   ├── AccountSummaryResponse.java   # 계좌평가현황요청(kt00004) 응답 DTO (record)
│   └── HoldingResponse.java          # 계좌 보유종목 1건 응답 DTO (record)
├── util/
│   ├── StockCodeUtils.java           # 거래소 라우팅 접미사(_AL/_NX) 제거 (bareCode)
│   └── KiwoomValueUtils.java         # 거래량 오류 센티널(2^32-1) 방어 파싱
├── interceptor/
│   └── ApiKeyInterceptor.java        # X-API-KEY 헤더 인증 (/api/trade/**)
├── config/
│   ├── WebMvcConfig.java             # 인터셉터 경로 등록
│   └── OpenApiConfig.java            # springdoc-openapi(Swagger) 설정 — public-api 그룹(/api/stock,/api/inds,/api/etf,/api/theme,/api/chart)
├── Repository/
│   ├── UserRepository.java           # users 테이블 (로그인 로직은 미구현)
│   └── LoginLogRepository.java       # login_log 테이블
├── User.java                         # users 테이블 엔티티 (no/id/pw/pinpw)
├── LoginLog.java                     # login_log 테이블 엔티티 (no/access_ip/access_time/user_id)
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
| ka00198 | 실시간종목조회순위 | fn_ka00198 | ✅ (컨트롤러에서 `SearchRankItemResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10099 | 종목정보 리스트 | fn_ka10099 | ✅ |
| ka10001 | 주식기본정보요청 | fn_ka10001 | ✅ (컨트롤러에서 `StockBasicInfoResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10003 | 체결정보요청 | fn_ka10003 | ✅ |
| ka10023 | 거래량급증요청 | fn_ka10023 | ✅ |
| ka10027 | 전일대비등락률상위요청 | fn_ka10027 | ✅ |
| ka10030 | 당일거래량상위요청 | fn_ka10030 | ✅ (컨트롤러에서 `RankItemResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10032 | 거래대금상위요청 | fn_ka10032 | ✅ (컨트롤러에서 `RankItemResponse` DTO로 변환 후 반환, 2026-07-15) |
| ka10095 | 관심종목정보요청 | fn_ka10095 | ✅ |
| ka10100 | 종목정보조회 | fn_ka10100 | ✅ |
| ka10101 | 업종코드 리스트 | fn_ka10101 | ✅ |

### 테마 정보 조회 (KiwoomThemeInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka90001 | 테마그룹별요청 | fn_ka90001 | ✅ (컨트롤러에서 `ThemeGroupResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka90002 | 테마구성종목요청 | fn_ka90002 | ✅ (컨트롤러에서 `ThemeConstituentResponse` DTO로 변환 후 반환, 2026-08-20 / `changeAmount`·`volume`(nullable) 필드 보강 2026-08-24) |

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

### 차트 정보 조회 (KiwoomChartInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka10079 | 주식틱차트조회요청 | fn_ka10079 | ✅ (컨트롤러에서 `IntradayCandleResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10080 | 주식분봉차트조회요청 | fn_ka10080 | ✅ (컨트롤러에서 `IntradayCandleResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10081 | 주식일봉차트조회요청 | fn_ka10081 | ✅ (컨트롤러에서 `CandleResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10082 | 주식주봉차트조회요청 | fn_ka10082 | ✅ (컨트롤러에서 `CandleResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10083 | 주식월봉차트조회요청 | fn_ka10083 | ✅ (컨트롤러에서 `CandleResponse` DTO로 변환 후 반환, 2026-08-20) |
| ka10094 | 주식년봉차트조회요청 | fn_ka10094 | ✅ (컨트롤러에서 `CandleResponse` DTO로 변환 후 반환, 2026-08-20) |

### 계좌 정보 조회 (KiwoomAccountInfoService) — ⚠️ `/api/account/**`, X-API-KEY 인증 필요
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| kt00004 | 계좌평가현황요청 | fn_kt00004 | ✅ (컨트롤러에서 `AccountSummaryResponse`/`HoldingResponse` DTO로 변환 후 반환, 2026-08-21) |

### 업종 정보 조회 (KiwoomIndsInfoService)
| TR | 기능 | 서비스 메서드 | 상태 |
|---|---|---|---|
| ka20001 | 업종현재가요청 | fn_ka20001 | ✅ (컨트롤러에서 `IndexResponse` DTO로 변환) |
| ka20002 | 업종별주가요청 | fn_ka20002 | ✅ |
| ka20003 | 전업종지수요청 | fn_ka20003 | ✅ (컨트롤러에서 `IndustryRankResponse` DTO로 변환 후 반환, 2026-08-24) |
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
- [o] `application.properties` 환경변수화 — EC2 `kiwoom_api.yml` 환경변수 참조로 변경, `.env` 파일로 주입
- [o] Gradle 빌드 확인 — `./gradlew build` 로 JAR 생성되는지 확인
- [o] 함수명 오타 수정 — `fn_ka00199` → `fn_ka10099`, `fn_ka00100` → `fn_ka10100`
- [o] 변수명 오타 수정 — `TokenService`의 `mockhost` → `host`
- [o] 불필요한 import 제거 — `KiwoomETFInfoService`의 `Param` import
- [o] `fn_ka40004` 파라미터 하드코딩 → 파라미터화
- [o] 순위정보 TR 추가 — ka10023(거래량급증), ka10027(등락률상위), ka10030(거래량상위), ka10032(거래대금상위)

### 로컬 개발 환경 (VS Code)
- [o] Postgres 없이 로컬에서 조회 API만 테스트할 수 있는 `local` 프로필 구성 (2026-07-15)
  - `core/src/main/resources/application-local.properties` — DataSource/Hibernate/JpaRepositories 자동설정 제외 + 로컬 `kiwoom_api.yml`(classpath) 사용
  - `.vscode/launch.json` — `SPRING_PROFILES_ACTIVE=local`로 실행하는 설정 추가
  - `application.properties`(배포용 공유 파일)는 그대로 두고 프로필로만 분리 — 운영(docker-compose)은 이 프로필을 켜지 않아 영향 없음
  - 실행: `SPRING_PROFILES_ACTIVE=local ./gradlew bootRun` (VS Code "Run and Debug" 패널로 실행하려면 Java 디버거 확장 필요, 터미널 실행은 확장 불필요)

### 프론트엔드(Vue) 연동 준비
- [o] ka10032(거래대금상위) — `KiwoomStockController`에서 `KiwoomSTKInfoService.fn_ka10032`가 반환한 raw JSON을 파싱해 `RankItemResponse` record(rank/code/name/currentPrice/changeRate)로 변환 후 반환하도록 변경 (2026-07-15). 키움 응답의 `+`/`-` 부호 붙은 숫자 문자열은 `Long.parseLong`/`Double.parseDouble`이 선행 부호를 그대로 처리해 별도 파싱 로직 불필요함을 확인
- [o] ka20001(업종현재가요청, 코스피/코스닥 지수) — `KiwoomIndsController`에서 `KiwoomIndsInfoService.fn_ka20001`이 반환한 raw JSON을 파싱해 `IndexResponse` record(market/indexValue/changeValue/changeRate)로 변환 후 반환하도록 변경 (2026-08-20). `market`은 응답 JSON에 텍스트로 없어 요청 파라미터 `inds_cd`(001→KOSPI, 101→KOSDAQ)로 매핑. 로컬에서 KOSPI/KOSDAQ 둘 다 정상 응답 확인 완료
- [o] 메인 화면 "거래량 상위 / 거래대금 상위 / 검색 상위 / 글로벌 인기 업종·테마" 4개 위젯용 API 구현 완료 (2026-08-20)
  - ka10030(당일거래량상위) — `RankItemResponse` 재사용 (ka10032와 응답 구조 동일). 단, ka10030 응답엔 순위 필드가 없어 배열 순서를 1부터 매겨서 `rank`로 사용
  - ka00198(실시간종목조회순위, "검색 상위") — `SearchRankItemResponse` record(rank/code/name/currentPrice/changeRate/rankChange/rankChangeSign) 신규 추가. `rank_chg`가 순위 변동 없을 때 빈 문자열로 오는 걸 확인해 0으로 안전 파싱 처리
  - ka90001(테마그룹별, "글로벌 인기 업종·테마") / ka90002(테마구성종목) — 기존에 없던 TR이라 `KiwoomThemeInfoService`(`/api/dostk/thme`) + `KiwoomThemeController`(`/api/theme`) 신규 생성. `ThemeGroupResponse`, `ThemeConstituentResponse` record 추가
  - **주의**: 키움 REST API 스펙에 `/api/us/...` 카테고리는 있지만 `thme`(테마)는 국내(`/api/dostk/thme`)에만 존재 — "미국" 테마 토글은 이 TR로 구현 불가, 별도 방법 필요 (미착수)
- [o] **버그 수정**: `cur_prc`/`past_curr_prc` 파싱 시 하락 종목의 가격이 음수로 노출되던 문제 (2026-08-20) — 키움 응답의 `+`/`-`는 "전일 대비 방향" 표시일 뿐 실제 가격 부호가 아닌데, `Long.parseLong`/`Double.parseDouble`이 부호까지 그대로 값에 반영해버림. `RankItemResponse`(ka10032/ka10030), `SearchRankItemResponse`(ka00198), `ThemeConstituentResponse`(ka90002), `IndexResponse`(ka20001) 4곳의 `currentPrice`/`indexValue` 파싱에 전부 `Math.abs()` 추가해 절대값만 취하도록 수정. 등락 방향은 `changeRate`(flu_rt/base_comp_chgr 등)가 별도 부호로 갖고 있어 중복이었음. 프론트 `stockDisplay.ts`의 `Math.abs()` 방어 코드는 무해하니 그대로 둬도 됨
- [o] 캔들/거래량 차트 API 신규 구현 (2026-08-20) — 프론트에서 캔들차트/거래량차트/이동평균선을 그릴 API가 없어서 요청받음
  - `KiwoomChartInfoService`(`/api/dostk/chart`) + `KiwoomChartController`(`/api/chart`) 신규 생성
  - 일/주/월/년봉(ka10081/82/83/94) → `CandleResponse`(date/open/high/low/close/volume), 틱/분봉(ka10079/80) → `IntradayCandleResponse`(dateTime/open/high/low/close/volume)
  - `GET /api/chart/{daily|weekly|monthly|yearly}/{stockCode}?baseDt=&updStkpcTp=`, `GET /api/chart/{minute|tick}/{stockCode}?ticScope=&baseDt=&updStkpcTp=` — `baseDt` 미지정 시 서버가 오늘 날짜(YYYYMMDD)로 기본 조회
  - **이동평균선은 키움 API에 별도 TR이 없음** — 프론트에서 응답의 `close` 값으로 직접 계산해야 함 (표준적인 차트 라이브러리 방식)
  - `KiwoomValueUtils.parseVolume()` 신규 추출 — ka10001에서 발견된 "거래량이 정확히 4294967295(2³²−1)면 데이터 없음으로 null 처리" 로직을 차트 API에도 동일 적용하기 위해 공용 유틸로 승격 (`KiwoomStockController`도 이 유틸로 리팩터링)
  - 로컬 검증: daily/weekly/minute/tick 4개 엔드포인트 모두 실제 응답 확인 완료
  - 프론트 전달용 상세 API 문서를 Claude Artifact(HTML)로 제작해 전달 (2026-08-20) — 엔드포인트별 파라미터/응답 필드/실제 예시 JSON/이동평균선 계산 가이드(TS 예시코드) 포함. 링크는 비공개 상태라 공유 시 아티팩트 페이지에서 직접 공개 전환 필요
- [o] ka10001(주식기본정보요청) — `StockBasicInfoResponse` record(code/name/currentPrice/changeAmount/changeRate/openPrice/highPrice/lowPrice/volume)로 변환 (2026-08-20). `currentPrice`/`openPrice`/`highPrice`/`lowPrice`는 `Math.abs()`로 절대값, `changeAmount`/`changeRate`는 방향 표시라 부호 유지. 프론트에서 `parseSignedPrice`/`parseSignedRate` 파싱 로직 제거 가능
  - [o] **개선**: `code` 필드에 거래소 라우팅 접미사(`_AL`/`_NX`)가 API마다 다르게 붙어 종목 식별이 어려운 문제 (2026-08-20) — 원인은 `stex_tp`(거래소구분) 요청 파라미터. `ka10030`/`ka10032`는 컨트롤러 기본값이 `stexTp=3`(통합)이라 응답 `stk_cd`에 `_AL`이 붙지만, `ka00198`(검색상위)은 애초에 `stex_tp` 파라미터 자체가 없는 TR이라 접미사가 안 붙음 — 키움이 임의로 다르게 주는 게 아니라 TR 스펙 차이. `_AL`/`_NX`는 실제 라우팅 정보라 `code` 필드는 그대로 유지하고, `StockCodeUtils.bareCode()`로 접미사를 제거한 `bareCode` 필드를 `RankItemResponse`/`SearchRankItemResponse`/`ThemeConstituentResponse`/`StockBasicInfoResponse` 4곳에 추가. 프론트는 즐겨찾기/최근조회 등 종목 식별용으로 `bareCode`를 키로 사용
  - [o] **버그 수정**: `volume`(원본 `trde_qty`) 일부 종목에서 비정상적으로 큰 값 노출 (2026-08-20) — 디버그 로그로 원본을 직접 확인한 결과 부호 파싱 오버플로우가 아니라 **키움 원본 데이터 자체 문제**였음. `252670`(KODEX 200선물인버스2X)의 `trde_qty`가 정확히 `4294967295`(2³²−1)로 오는데, 이는 int32 `-1`(에러/데이터없음 센티널)이 unsigned로 잘못 캐스팅됐을 때 나오는 값과 정확히 일치 — 키움 쪽 버그로 판단. 반면 `114800`(KODEX 인버스)의 `1493371621`(약 14.9억주)은 저가+초고회전 ETF 특성상 실제 값일 가능성이 있어 그대로 둠. 프론트 요청에 따라 `volume`을 `long` → `Long`(nullable)로 변경하고, `trde_qty == 4294967295L`인 경우만 `null`(데이터 없음) 처리, 그 외 큰 값은 그대로 pass-through. 프론트는 `volume: number | null`로 이미 대응 완료
- [o] 계좌 자산현황 API 신규 구현 (2026-08-21) — 프론트에서 "내 투자" 화면(계좌평가금액/당일손익/예수금/보유종목 리스트)용 API 요청받음
  - `kt00004`(계좌평가현황요청) 채택 — `kt00018`(계좌평가잔고내역)도 검토했으나 예수금 필드가 없어서 제외
  - `KiwoomAccountInfoService`(`/api/dostk/acnt`) + `KiwoomAccountController`(`GET /api/account/summary`) 신규 생성, `AccountSummaryResponse`(totalAssetValue/deposit/todayProfitLoss/todayProfitLossRate/holdings) + `HoldingResponse`(code/name/quantity/avgPrice/currentPrice/evaluationAmount/profitLossAmount/profitLossRate) record 추가
  - **다른 조회 API와 달리 실제 계좌 자산/보유종목 데이터라 `/api/trade/**`와 동일하게 `X-API-KEY` 인증 필요** — `WebMvcConfig`의 인터셉터 경로에 `/api/account/**` 추가, `springdoc.paths-to-exclude`에도 추가해 Swagger 공개 문서에서 제외. 계좌별 사용자 인증(JWT 로그인)은 아직 없어서 현재는 서버에 연결된 단일 계좌만 조회 가능 — 멀티유저 지원은 로그인 기능 완료 후 별도 작업
  - `stk_cd`가 다른 TR과 달리 접두어 방식(`A005930` = 접두어 1자리 + 종목코드 6자리, A:주식/J:ELW/Q:ETN)이라 `StockCodeUtils.bareCode()`(접미사 제거용)를 못 쓰고 `substring(length-6)`으로 별도 처리
  - 로컬(`realhost`=실전투자)에서 API Key 없이 403, API Key 포함 시 정상 응답(현재 보유종목 없어 0/빈 배열) 확인 완료
- [o] ka20003(전업종지수요청) 신규 구현 (2026-08-24) — 프론트 요청("당일 업종 등락률" 위젯)으로 확인 중 미구현 TR로 발견. `KiwoomIndsController`에 `GET /api/inds/ranking?indsCd=001`(코스피)/`101`(코스닥) 신규 추가, `IndustryRankResponse`(industryCode/industryName/indexValue/changeValue/changeRate/volume/risingStockCount/fallingStockCount/stockCount) record 추가
  - **파라미터 주의**: 요청 스펙엔 `mrkt_tp`/`inds_cd` 둘 다 필수지만, 실측 결과 응답을 실제로 결정하는 건 `inds_cd`(001→코스피 업종 전체, 101→코스닥 업종 전체)이고 `mrkt_tp`는 값과 무관하게 무시됨(로컬에서 `mrkt_tp=1`+`inds_cd=001` 조합으로 실제 확인). 컨트롤러는 `indsCd`만 파라미터로 받고 `mrkt_tp`는 내부에서 유도
  - ka20001(업종 1개, 현재 스냅샷 + 당일 시간대별 흐름)·ka20009(업종 1개, 최근 20영업일 일별 이력)와 달리 ka20003은 시장 전체 업종(코스피 33개/코스닥 다수)을 한 번에 배열로 반환 — "업종 랭킹" 성격 화면엔 이걸 쓰고, 단일 지수(코스피/코스닥 종합) 위젯엔 기존 ka20001 유지
  - **버그 수정**: `ThemeConstituentResponse`(ka90002)에 `changeAmount`(원본 `pred_pre`) 필드 누락돼있던 것 추가, `accTradeVolume`(long) → `volume`(nullable `Long`, `KiwoomValueUtils.parseVolume()` 센티널 방어 적용)로 다른 DTO들과 명명/타입 통일 — 프론트 리포트(`/theme/:id` 상세 페이지 스펙 확인 요청)에서 지적된 갭
- [ ] 나머지 6개 조회 TR(ka10023, ka10095, ka10099, ka10003, ka10100, ka10101)은 아직 raw String 그대로 반환 — 프론트에서 필요해질 때마다 같은 패턴으로 순차 변환 예정. TR마다 필드가 완전히 달라 10개를 하나의 공용 DTO/제네릭 파싱 헬퍼로 통합하는 건 시기상조로 판단해 보류 (2~3번째 변환 시 반복 패턴 보이면 그때 추출)
- [ ] Vue 지수 위젯에 `POST /api/inds/current-price`(mrkt_tp/inds_cd로 KOSPI: 0/001, KOSDAQ: 1/101 각각 호출) 실제 연동 (StockFor 저장소 밖 작업)
- [ ] Vue `SearchView.vue` 거래대금 탭에 `GET /api/stock/trading-value-ranking` 실제 연동 (StockFor 저장소 밖 작업)
- [o] `/api/trade/**` 제외한 조회 API만 프론트에 문서로 공유 — springdoc-openapi(Swagger UI) 자동 문서화로 결정 변경, StockFor 저장소에 구성 완료 (2026-08-20)
  - `config/OpenApiConfig.java`: `/api/stock/**`, `/api/inds/**`, `/api/etf/**`, `/api/theme/**`, `/api/chart/**`만 포함하는 `public-api` 그룹 정의
  - `application.properties`: `springdoc.paths-to-exclude=/api/trade/**,/api/kiwoom/**,/api/admin/**`로 기본(그룹 미지정) 문서에서도 주문/토큰테스트/관리자 엔드포인트 전역 제외 — 이중 안전장치
  - 로컬 검증: `/v3/api-docs/public-api`, `/v3/api-docs`(기본) 둘 다 trade/kiwoom/admin 경로 미노출, `/swagger-ui/index.html` 정상 로드 확인
  - 배포 후 접근 경로: `http://<EC2 IP>/swagger-ui/index.html`

### EC2 배포를 위해 해야 하는 작업

> **현재 상태 (2026-08-21): EC2 배포 전체 중단.** 과금이 시작되어 모든 배포를 내린 상태 — 프리티어 초과, 미연결 Elastic IP, 데이터 전송 등 원인 미파악. **원인 확인 후 재배포 예정.** 그 전까지 계좌 API 등 신규 기능은 로컬(`local` 프로필)에서만 개발/검증하고, HTTPS/재배포 관련 작업은 보류.

- [o] EC2 인스턴스 생성 (Ubuntu 26.04 LTS)
- [o] EC2에 Java 설치 (OpenJDK 21)
- [o] 보안 그룹 설정 — 8080 포트 인바운드 오픈
- [o] JAR 파일 EC2로 업로드 (`scp` 사용, WSL에서 실행)
- [o] EC2에 `kiwoom_api.yml` 직접 생성 (외부 설정 파일 방식)
- [o] 키움 API IP 화이트리스트에 EC2 IP 등록 (13.x.x.x)
- [o] EC2에서 nohup 백그라운드 실행 확인
- [o] API 정상 동작 확인 — ka10001 삼성전자 기본정보 조회 성공
- [o] nginx 리버스 프록시 설정 — 80포트로 8080 포워딩 완료 (2026-06-30)
- [o] systemd 서비스 등록 — start.sh 방식으로 EC2 재시작 시 자동 실행, 앱 죽으면 자동 재시작 (2026-06-30)
- [o] EC2에 Docker 설치 완료 (2026-07-01) — Docker 29.1.3, Compose v2 2.40.3
- [o] EC2에서 git clone 후 docker compose up 실행 — 기존 systemd 서비스 교체 완료 (2026-07-13). `stockfor-app`/`stockfor-nginx` 컨테이너로 전환, ka10001 삼성전자 기본정보 조회로 동작 확인
- [ ] AWS CloudWatch 연동 — 로그 모니터링
- [o] GitHub Actions CI/CD 구성 완료 (2026-07-13)
  - `.github/workflows/ci.yml`: main push/PR 시 `./gradlew build` 자동 실행
  - `.github/workflows/deploy.yml`: 수동 실행(workflow_dispatch) 시 JAR 빌드 → EC2 scp 전송 → `git pull` + `docker compose build && up -d`
  - Secrets 등록 완료: `EC2_HOST`, `EC2_USER`, `EC2_SSH_KEY`
  - Deploy 워크플로우 최초 수동 실행 성공, ka10001 삼성전자 기본정보 조회로 실제 배포 동작 확인
  - 트리거는 의도적으로 수동(workflow_dispatch) — push 자동배포로 바꾸려면 deploy.yml의 `on:`을 `push: branches: [main]`로 변경

## 보안 TODO

### 지금 당장
- [o] AWS 보안 그룹 8080 포트 차단 — 처음부터 보안그룹에 없었음, nginx 80포트로만 접근 (2026-06-30 확인)
- [ ] 블로그 발행 전 EC2 IP 마스킹 — 현재 파일 기준으로는 마스킹 완료. 단, git 히스토리(`log_aws_20260618_1544.md` 등 과거 커밋)에 실제 IP가 남아있음 확인 (2026-07-13). 저장소가 public이라 완전히 가려지진 않음 — 보안그룹/API Key 인증으로 위험도는 낮다고 판단해 우선순위 낮춤. 추후 EC2 Elastic IP 재발급으로 기존 IP 무효화 예정
- [o] `logging.level.root=DEBUG` → `WARN` 으로 변경 후 재배포 — 로컬 수정 완료 + EC2 재배포 완료 (2026-06-30)

### 단기 (CI/CD 작업 전후)
- [o] 매수/매도 엔드포인트 API Key 인증 추가 — `ApiKeyInterceptor` + `WebMvcConfig(/api/trade/**)` 구현 완료, `APP_API_KEY` .env 주입 완료 (2026-06-30)
- [ ] Swagger UI 접근 제한 — 검토 결과 `public-api` 그룹에는 인증 불필요한 조회 API(`/api/stock`, `/api/inds`, `/api/etf`, `/api/theme`, `/api/chart`)만 노출되고, `/api/trade`(API Key 보호), `/api/account`(API Key 보호, 2026-08-21 추가), `/api/kiwoom`(appkey/mykey 사용 테스트용), `/api/admin`은 `springdoc.paths-to-exclude`로 문서에서 제외되어 있어 공개 위험은 낮다고 판단 (2026-08-20). IP 제한은 여전히 하지 않은 상태 — 필요해지면 나중에 처리

### 중기 (로그인 페이지 작업 시)
- [ ] nginx Basic Auth 또는 Spring Security + JWT 로그인 구현 — 방식은 **JWT로 방향 결정** (2026-07-13). Vue 프론트와 API가 다른 origin인데 HTTPS가 아직 없어서 세션 쿠키 방식은 SameSite 정책 때문에 cross-origin에서 동작 안 함 → JWT는 헤더 기반이라 HTTPS 유무와 무관하게 지금 바로 동작 가능해서 채택
  - **"가장 강한 보안" 관련 논의 및 결정 방향 (2026-08-21)**: (1) access token은 프론트 메모리에만 보관, `localStorage` 저장 금지 (XSS로 탈취 방지) (2) refresh token은 httpOnly+Secure+SameSite=Strict 쿠키로 발급 (JS가 접근 불가) (3) access token 수명은 짧게(5~15분) (4) refresh token 재사용 탐지 시 해당 계정 전체 세션 강제 로그아웃(rotation) (5) 이미 있는 `login_log` 테이블로 접속 IP/시간 감사 로그 활용 (6) 이 모든 게 의미 있으려면 HTTPS가 선행되어야 함(평문 전송이면 토큰 탈취 방지책이 무의미)
  - [o] PostgreSQL 컨테이너 및 `users`(no/id/pw/pinpw), `login_log`(no/access_ip/access_time/user_id) 테이블·엔티티·Repository 준비 완료 (2026-07-13) — `User.java`, `LoginLog.java`, `Repository/UserRepository.java`, `Repository/LoginLogRepository.java`
  - [ ] 실제 로그인 로직(Spring Security + JWT 발급/검증, 비밀번호 BCrypt 인코딩, 계정 시딩)은 다음 단계에서 진행 — 아직 미착수
  - [ ] EC2 `/home/ubuntu/.env`에 `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` 추가 필요 (배포 전)
- [x] 경로별 권한 분리 (부분 완료, 2026-08-21) — `/api/stock`·`/api/inds`·`/api/etf`·`/api/theme`·`/api/chart`는 인증 불필요 / `/api/trade`·`/api/account`는 `X-API-KEY` 필수로 이미 분리됨 (`WebMvcConfig`). 단 이건 "로그인 필수"가 아니라 고정 API Key 방식이라, 사용자별 로그인(JWT) 붙는 게 다음 단계
- [ ] HTTPS 적용 — Let's Encrypt 무료 인증서, nginx(Docker)에서 처리. **EC2 배포가 과금 문제로 전체 중단된 상태([[project-ec2-billing-pause]])라 현재 보류.** 재배포 시 절차 (2026-08-21 논의 정리):
  1. EC2 Elastic IP 고정 → DuckDNS 등 무료 서브도메인을 그 IP로 연결 (A 레코드)
  2. 보안그룹에 443 포트 인바운드 추가
  3. EC2 호스트에 `certbot` 설치 → `docker compose stop nginx` → `certbot certonly --standalone -d <서브도메인>` → `docker compose start nginx`로 인증서 발급 (nginx가 Docker 컨테이너라 컨테이너 밖에서 발급 후 마운트하는 방식)
  4. `docker-compose.yml`의 nginx 서비스에 443 포트 + `/etc/letsencrypt` 볼륨 마운트 추가, `nginx/default.conf`에 80→443 리다이렉트 + SSL 서버 블록 추가 (아직 파일 수정 안 함 — 도메인 정해지면 반영 예정)
  5. `certbot renew` cron 자동 갱신 등록 (90일 만료, `--deploy-hook "docker restart stockfor-nginx"`)

> **주의:** 키움 IP 화이트리스트는 "다른 서버가 키움에 직접 요청하는 것"만 막음.
> "아무나 내 앱을 거쳐서 키움에 요청하는 것"은 앱 레벨 인증으로 별도로 막아야 함.

---

## EC2 서버 정보
- IP: 13.x.x.x
- OS: Ubuntu 26.04 LTS
- Java: OpenJDK 21 (컨테이너 내부, 호스트에는 불필요)
- 외부 포트: 80 (nginx 컨테이너) / 내부 포트: 8080 (springboot 컨테이너, 외부 미노출)
- 저장소: /home/ubuntu/StockFor (git clone)
- JAR: /home/ubuntu/StockFor/core/build/libs/core-0.0.1-SNAPSHOT.jar (로컬에서 빌드 후 scp로 전송, 컨테이너 내부 빌드 안 함 — EC2 저사양 메모리 제약)
- 설정: /home/ubuntu/kiwoom_api.yml (환경변수 참조: `${KIWOOM_APP_KEY}`, `${KIWOOM_MY_KEY}`, `${APP_API_KEY}`)
- 환경변수: /home/ubuntu/.env (chmod 600, `KEY=value` 형태 — docker compose `env_file`은 `export` 미지원, 2026-07-13부터 export 제거)
- 로그: `docker compose logs -f springboot` (2026-07-13부터, 기존 journalctl 아님)
- SSH 키: ~/.ssh/stockFor.pem (WSL 경로)
- 실행 방식: Docker Compose (`stockfor-app`, `stockfor-nginx`, `stockfor-postgres` 컨테이너) — 2026-07-13부터, 기존 systemd(`stockfor.service`)는 stop/disable 처리
- PostgreSQL 데이터: `/home/ubuntu/postgres-data` (바인드 마운트, 컨테이너 재생성해도 유지됨)

### SSH 접속
```bash
ssh -i ~/.ssh/stockFor.pem ubuntu@13.x.x.x
```

### 재배포 순서
```bash
# 1. 로컬 (core/ 디렉토리)
./gradlew bootJar

# 2. 로컬 → EC2 JAR 업로드
scp -i ~/.ssh/stockFor.pem \
  core/build/libs/core-0.0.1-SNAPSHOT.jar \
  ubuntu@13.x.x.x:/home/ubuntu/StockFor/core/build/libs/core-0.0.1-SNAPSHOT.jar

# 3. EC2에서 최신 코드 반영 및 재빌드
cd /home/ubuntu/StockFor
git pull
docker compose build
docker compose up -d
```

### 서비스 관리 명령어
```bash
docker compose up -d          # 시작 (백그라운드)
docker compose down           # 중지 및 컨테이너 제거
docker compose restart        # 재시작
docker compose ps             # 상태 확인
docker compose logs -f springboot   # 실시간 로그 (springboot)
docker compose logs --tail=50 springboot  # 최근 50줄 로그
```

### 트러블슈팅 (2026-06-30 기록)
| 문제 | 원인 | 해결 |
|---|---|---|
| `Could not resolve placeholder` | `.env`에 `export` 없어서 Java 프로세스에 환경변수 미전달 | `export VAR=값` 형태로 수정 |
| `Port 8080 already in use` | 이전 프로세스 살아있음 | `kill -9 $(lsof -t -i:8080)` |
| `scp: No such file or directory` | EC2에서 scp 실행 | WSL(로컬)에서 실행해야 함 |
| systemd `EnvironmentFile` 환경변수 미전달 | `export KEY=value` 형식은 systemd가 지원 안 함 | start.sh에서 `source .env` 후 java 실행하는 방식으로 해결 |
| docker compose `env_file` 환경변수 미전달 (2026-07-13) | `.env`에 systemd용 `export KEY=value` 형식이 남아있었음 — docker compose의 `env_file`은 `export` 접두사를 지원하지 않음 | `.env`에서 `export ` 접두사 제거, 중복 줄 정리 (`KEY=value` 형식으로) |

### TODO (코드 수정 필요)
- [o] `spring.jpa.open-in-view=false` — application.properties에 추가 완료, 재배포 완료 (2026-06-30)
- [o] `System.out.println` → `log` 교체 완료 (2026-07-01)
  - `KiwoomController`: appkey/mykey 출력 삭제(보안), 나머지 → `log.debug()`
  - `TokenService`: 토큰발급 성공 → `log.info()`
  - `StockService`: 테스트 URL → `log.debug()`
- [o] CORS 설정 추가 (2026-07-01) — `WebMvcConfig.addCorsMappings()`, 환경변수 `CORS_ALLOWED_ORIGINS` 주입
- [o] `application.properties` gitignore 제거 후 커밋 (2026-07-01) — 키움 API 키는 `kiwoom_api.yml`에만 존재
- [o] EC2 `.env`에 `CORS_ALLOWED_ORIGINS=http://13.x.x.x` 추가 완료 (2026-07-13)
- [o] `stock_prices` 테이블 용도 확인 및 정리 완료 (2026-07-13) — data.go.kr(공공데이터포털) 연동 기능이었으나 Kiwoom API와 무관한 독립 기능이고, H2 인메모리라 재배포 시 데이터 유실되는 구조라 실사용 가치 낮다고 판단해 전체 삭제
  - 조사 중 `application.properties`에 data.go.kr API 키(`stock.api.key_in/key_de`)가 평문으로 커밋되어 있던 것 발견(public 저장소 노출) — 기능 자체를 삭제하는 것으로 대응
  - 삭제: `StockController`, `StockService`, `StockPrice`(유일한 JPA 엔티티), `StockPriceRepository`, `StockApiResponseDto`, `StockApiItemDto`, 관련 테스트
  - `build.gradle`에서 `spring-boot-starter-data-jpa`, `h2` 의존성 제거, `application.properties`에서 `spring.datasource.*`/`spring.jpa.*` 제거
  - [o] data.go.kr 서비스 키 재발급(기존 노출 키 폐기) 완료 (2026-07-13) — git 히스토리에 남은 옛 키값은 이미 무효화되어 더 이상 위험하지 않음
