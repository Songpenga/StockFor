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
│   ├── Dockerfile                        # 멀티스테이지 빌드 (gradle:8-jdk21 → eclipse-temurin:21-jre)
│   └── src/main/java/hello/coreStock/
├── nginx/
│   └── default.conf                      # Docker용 nginx 설정 (proxy → springboot:8080)
└── docker-compose.yml                    # springboot + nginx 컨테이너 구성
```

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
├── interceptor/
│   └── ApiKeyInterceptor.java        # X-API-KEY 헤더 인증 (/api/trade/**)
├── config/
│   └── WebMvcConfig.java             # 인터셉터 경로 등록
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
- [o] `application.properties` 환경변수화 — EC2 `kiwoom_api.yml` 환경변수 참조로 변경, `.env` 파일로 주입
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
- [ ] Swagger UI 접근 제한 — 보안 그룹을 내 IP로 좁히면 같이 해결되나, 나중에 공개 시 별도 처리 필요

### 중기 (로그인 페이지 작업 시)
- [ ] nginx Basic Auth 또는 Spring Security + JWT 로그인 구현 — 어머니 사용 + 포트폴리오 고려, 방식 미결정
- [ ] 경로별 권한 분리 — `/api/stock/**` 인증 불필요 / `/api/trade/**` 로그인 필수
- [ ] HTTPS 적용 — Let's Encrypt 무료 인증서, nginx에서 처리

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
- 실행 방식: Docker Compose (`stockfor-app`, `stockfor-nginx` 컨테이너) — 2026-07-13부터, 기존 systemd(`stockfor.service`)는 stop/disable 처리

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
  - ⚠️ data.go.kr 서비스 키는 git 히스토리에는 남아있음 — 마이페이지에서 재발급(폐기) 권장, 아직 미완료
