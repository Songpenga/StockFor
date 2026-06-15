# 취업 준비 TODO

> 작성일: 2026-06-15  
> 목표: 오토위니(Autowini) 및 서비스 개발 회사 이직

---

## StockFor 프로젝트로 해결된 것

### ✅ AWS 배포 가능 확인
- 키움증권 API가 **HTTP REST 방식** (`https://api.kiwoom.com`) 으로 구현됨
- Windows COM API가 아님 → Linux EC2 배포 제약 없음
- 배포 방법: jar 빌드 → EC2 업로드 → java -jar 실행

### ✅ Spring Boot 실무 경험 없음 → 해소
- 현재 프로젝트가 **Spring Boot 3.3.7** 로 구현되어 있음
- JPA, Swagger(springdoc-openapi), Lombok 적용
- "학원에서만 썼다" → "개인 프로젝트에서 Spring Boot 3.x 실제 적용" 으로 이력서 수정 가능

### ✅ API 키 보안 안전
- `kiwoom_api.yml`, `application.properties` 모두 git에 미추가 상태
- GitHub에 키 노출 없음

### ✅ Swagger UI 포함
- 배포 후 `http://EC2 IP:8080/swagger-ui/index.html` 로 API 목록 시각화
- 면접 포트폴리오 데모 즉시 가능

### ✅ 외부 API 연동 구조 확인
- 키움증권 REST API: OAuth2 Bearer 토큰 발급 + 자동 갱신 로직 구현
- 공공데이터 포털 API: 주가 정보 조회 (`apis.data.go.kr`)
- 토큰 만료 1시간 전 자동 갱신 (`synchronized` 처리)

---

## 내가 해야 할 것

### 🔴 최우선 (이번 주)

#### 1. AWS 배포 실행
새 Claude Code 세션에서 진행 (`C:\Users\USER\Documents\GitHub\StockFor`)

```
순서:
1. gradlew bootJar → jar 빌드
2. AWS EC2 t2.micro 생성 (Amazon Linux 2023, 프리티어)
3. 보안 그룹 8080 포트 인바운드 허용
4. Java 17 설치 (sudo dnf install java-17-amazon-corretto -y)
5. jar 업로드 (scp)
6. 환경변수로 API 키 주입 후 실행
7. Swagger UI 접속 확인
```

주의사항:
- H2 인메모리 DB라 서버 재시작 시 데이터 초기화됨
- 데이터 유지 필요 시 `jdbc:h2:file:./stockdb` 로 변경

#### 2. 이력서 Spring Boot 항목 수정
배포 완료 후 career_01.md 참고하여 아래 내용 추가/수정

```
수정 전: "Spring Boot — 학원 수준 (3년 전)"
수정 후: "Spring Boot 3.x — 개인 프로젝트 실 적용"

추가 항목:
- AWS EC2 환경 개인 프로젝트 배포 및 운영
- 키움증권 REST API + 공공데이터 API 연동
- Bearer 토큰 자동 갱신 로직 구현
- Swagger(springdoc-openapi) API 문서화
```

---

### 🟡 이번 주 내 (퇴근 후)

#### 3. 이력서 전체 재작성
`career_01.md` 의 6번 항목 참고

핵심 변경사항:
- 항목 순서 재배치: 강한 것(상태머신 설계, 성능 개선 1.7초) 앞으로
- 서술 방식 전환: 기능 나열 → 문제/해결/결과 구조
- 회원가입·로그인 항목 삭제 또는 한 줄 축소
- VWorld/Kakao API → 업무 이력 항목으로 이동
- 성능 개선 수치 보완: "1.7초 개선" → "X초 → Y초" 형태로

#### 4. 프론트엔드 작업
- 퇴근 후 또는 주말에 진행
- 백엔드 완성 상태이므로 연결만 하면 됨
- 배포된 Swagger로 API 확인하면서 작업 가능

---

### 🟢 다음 주

#### 5. 오토위니 지원
- 이력서 완성 + AWS 배포 완료 후 지원
- `career_01.md` 의 7번 면접 스토리 3개 숙지

#### 6. 병행 지원 회사 리스트업
추천 유형 (career_01.md 참고):
- 핀테크 스타트업 (키움증권 API 경험 활용)
- 부동산/물류 플랫폼 (주소정제, VWorld/Kakao 경험)
- B2B SaaS (복잡한 워크플로우 구현 경험)
- 중견 이커머스 (Oracle 쿼리/성능 튜닝 강점)

#### 7. Spring Boot 핵심 개념 복습
면접 대비용 (career_01.md 8번 참고)
- application.yml 설정 방식
- @SpringBootApplication, 내장 Tomcat
- Spring Data JPA vs MyBatis 차이
- REST API 규칙 (@GetMapping/@PostMapping/@PutMapping/@DeleteMapping)
- @ControllerAdvice 전역 예외 처리

---

## 참고 파일

| 파일 | 내용 |
|------|------|
| `career_01.md` | 이력서 전체 검토 결과, JD 비교, 수정 방향 |
| `claude_todo.md` | 이 파일 — 해야 할 것 목록 |

---

## 진행 현황

- [x] 양도양수 프로세스 코드 검증 완료
- [x] JD 분석 및 이력서 비교 완료
- [x] StockFor 프로젝트 구조 확인 완료 (HTTP REST, Spring Boot 3.x)
- [x] API 키 보안 확인 완료 (git 미포함)
- [ ] AWS EC2 배포
- [ ] 이력서 Spring Boot 항목 수정
- [ ] 이력서 전체 재작성
- [ ] 프론트엔드 작업
- [ ] 오토위니 지원
- [ ] 병행 지원 회사 리스트업
