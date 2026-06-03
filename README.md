StockFor — 시니어 친화 주식 거래 웹앱 (개발 중)

기존 증권 앱의 복잡한 UX를 어머니의 관점에서 재해석한 반응형 웹 애플리케이션입니다.

🛠 Tech Stack
- Backend:** Java 17, Spring Boot, Spring Data JPA
- Frontend:** Vue.js, TypeScript, Pinia, Tailwind CSS
- Database : H2 Database (테스트용)
- API : Kiwoom Open API

✨ 핵심 구현 및 도전 과제
1. 모던 프론트엔드 아키텍처 도입
   - 컴포넌트 기반 UI 개발을 위해 Vue.js와 정적 타이핑을 지원하는 TypeScript 도입
   - 시니어 가독성을 고려한 대형 UI 요소 및 단순화된 네비게이션 구조 설계
2. Spring Boot 기반 API 서버 구축
   - 증권사 OpenAPI 연동을 위한 인프라 구성 및 실시간 시세 데이터 바인딩 구조 설계
   - 비동기 처리를 통한 API 응답 효율화 고민 중
