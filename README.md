# StockFor

키움증권 REST API를 Spring Boot로 감싸서 국내 주식 정보 조회 및 주문을 처리하는 백엔드 서버입니다.

## 기술 스택

- Java / Spring Boot
- Gradle
- 키움증권 REST API

## 프로젝트 구조

```
core/src/main/java/hello/coreStock/
├── Controller/
│   ├── KiwoomController.java          # 토큰 발급 테스트용 (구버전)
│   ├── KiwoomTokenController.java     # 토큰 상태 조회
│   ├── KiwoomStockController.java     # 주식 정보 조회
│   ├── KiwoomETFController.java       # ETF 정보 조회
│   ├── KiwoomIndsController.java      # 업종 정보 조회
│   ├── KiwoomTradingController.java   # 주문 (매수/매도/정정/취소)
│   └── StockController.java           # 주가 데이터 DB 저장/조회
├── Service/
│   ├── KiwoomApiService.java          # 공통 HTTP 호출 (부모 클래스)
│   ├── TokenService.java              # 토큰 자동 발급/갱신
│   ├── KiwoomSTKInfoService.java      # 주식 정보 TR
│   ├── KiwoomETFInfoService.java      # ETF 정보 TR
│   ├── KiwoomIndsInfoService.java     # 업종 정보 TR
│   ├── KiwoomTradingService.java      # 주문 TR
│   └── StockService.java              # 주가 데이터 API 수집 및 DB 저장
├── Dto/
│   ├── sellNBuyOrderRequestDto.java   # 매수/매도 요청
│   ├── editOrderRequestDto.java       # 정정 요청
│   └── cancelOrderRequestDto.java     # 취소 요청
├── stockInterface/
│   └── StockPriceRepository.java      # 주가 DB 레포지토리
├── StockPrice.java                    # 주가 엔티티
├── StockApiResponseDto.java           # 주가 API 응답 DTO
└── StockApiItemDto.java               # 주가 API 아이템 DTO
```

## 환경 설정

`application.properties`에 아래 값을 설정하세요.

```properties
kiwoom.realhost=https://mockapi.kiwoom.com   # 모의투자 or https://api.kiwoom.com (실거래)
kiwoom.appkey=YOUR_APP_KEY
kiwoom.mykey=YOUR_SECRET_KEY
```

> 토큰은 서버 기동 후 첫 API 호출 시 자동 발급되며, 만료 1시간 전에 자동 갱신됩니다.

---

## API 목록

### 토큰 관리

| Method | 엔드포인트 | 기능 | 키움 TR |
|---|---|---|---|
| GET | `/api/admin/token/status` | 현재 토큰 상태 조회 | - |

---

### 주식 정보 조회 `/api/stock`

| Method | 엔드포인트 | 기능 | 키움 TR | 파라미터 |
|---|---|---|---|---|
| GET | `/api/stock/ranking` | 실시간 종목 조회 순위 | ka00198 | `queryType` (1:1분, 2:10분, 3:1시간, 4:당일누적, 5:30초) |
| GET | `/api/stock/marketList` | 종목 정보 리스트 | ka10099 | `marketCategory` (0:코스피, 10:코스닥, 8:ETF 등) |
| GET | `/api/stock/basic-info/{stockCode}` | 주식 기본 정보 | ka10001 | `stockCode` (예: 005930) |
| GET | `/api/stock/trade-info/{stockCode}` | 체결 정보 | ka10003 | `stockCode` |
| GET | `/api/stock/item-info/{stockCode}` | 종목 상세 정보 | ka10100 | `stockCode` |
| GET | `/api/stock/volume-surge` | 거래량 급증 | ka10023 | `mrktTp`, `sortTp`, `tmTp`, `trdeQtyTp`, `tm`, `stkCnd`, `pricTp`, `stexTp` |
| GET | `/api/stock/price-change-ranking` | 전일대비 등락률 상위 | ka10027 | `mrktTp`, `sortTp`, `trdeQtyCnd`, `stkCnd`, `crdCnd`, `updownIncls`, `pricCnd`, `trdePricaCnd`, `stexTp` |
| GET | `/api/stock/volume-ranking` | 당일 거래량 상위 | ka10030 | `mrktTp`, `sortTp`, `mangStkIncls`, `crdTp`, `trdeQtyTp`, `pricTp`, `trdePricaTp`, `mrktOpenTp`, `stexTp` |
| GET | `/api/stock/trading-value-ranking` | 거래대금 상위 | ka10032 | `mrktTp`, `mangStkIncls`, `stexTp` |
| GET | `/api/stock/interest-info` | 관심종목 정보 (복수 종목) | ka10095 | `stockCode` (복수 시 `\|` 구분, 예: `005930\|039490`) |
| GET | `/api/stock/industry-codes` | 업종코드 리스트 | ka10101 | `mrktTp` (0:코스피, 1:코스닥, 2:KOSPI200, 4:KOSPI100, 7:KRX100) |

---

### ETF 정보 조회 `/api/etf`

| Method | 엔드포인트 | 기능 | 키움 TR | 파라미터 |
|---|---|---|---|---|
| POST | `/api/etf/item-info` | ETF 종목 정보 | ka40002 | `stk_cd` |
| POST | `/api/etf/daily-history` | ETF 일별 추이 | ka40003 | `stk_cd` |
| GET | `/api/etf/market-all-price` | ETF 전체 시세 | ka40004 | `txonType`, `navpre`, `mngmcomp`, `txonYn`, `traceIdex`, `stexTp` |
| POST | `/api/etf/hourly-detail-trade` | ETF 시간대별 추이 | ka40006 | `stk_cd` |
| POST | `/api/etf/daily-trade` | ETF 일자별 체결 | ka40008 | `stk_cd` |
| POST | `/api/etf/hourly-contract` | ETF 시간대별 체결 | ka40009 | `stk_cd` |
| POST | `/api/etf/hourly-trend` | ETF 시간대별 추이 | ka40010 | `stk_cd` |

---

### 업종 정보 조회 `/api/inds`

| Method | 엔드포인트 | 기능 | 키움 TR | 파라미터 |
|---|---|---|---|---|
| POST | `/api/inds/current-price` | 업종 현재가 | ka20001 | `mrkt_tp`, `inds_cd` |
| POST | `/api/inds/stock-price` | 업종별 주가 | ka20002 | `mrkt_tp`, `inds_cd`, `stex_tp` |
| POST | `/api/inds/daily-current-price` | 업종 현재가 일별 | ka20009 | `mrkt_tp`, `inds_cd` |

#### 파라미터 참고

- `mrkt_tp`: `0` 코스피 / `1` 코스닥 / `2` 코스피200
- `inds_cd`: `001` 종합(KOSPI) / `002` 대형주 / `003` 중형주 / `101` 종합(KOSDAQ) 등

---

### 주문 `/api/trade`

| Method | 엔드포인트 | 기능 | 키움 TR | Request Body |
|---|---|---|---|---|
| POST | `/api/trade/buy` | 매수 주문 | kt10000 | `sellNBuyOrderRequestDto` |
| POST | `/api/trade/sell` | 매도 주문 | kt10001 | `sellNBuyOrderRequestDto` |
| POST | `/api/trade/edit` | 주문 정정 | kt10002 | `editOrderRequestDto` |
| POST | `/api/trade/cancel` | 주문 취소 | kt10003 | `cancelOrderRequestDto` |

#### 매수/매도 Request Body (`sellNBuyOrderRequestDto`)

```json
{
  "dmst_stex_tp": "KRX",
  "stk_cd": "005930",
  "ord_qty": "1",
  "ord_uv": "",
  "trde_tp": "3",
  "cond_uv": ""
}
```

- `dmst_stex_tp`: 거래소 구분 (`KRX` / `NXT` / `SOR`)
- `trde_tp`: 매매구분 (`0` 보통 / `3` 시장가 / `5` 조건부지정가 등)

#### 정정 Request Body (`editOrderRequestDto`)

```json
{
  "dmst_stex_tp": "KRX",
  "orig_ord_no": "0000139",
  "stk_cd": "005930",
  "mdfy_qty": "1",
  "mdfy_uv": "199700",
  "mdfy_cond_uv": ""
}
```

#### 취소 Request Body (`cancelOrderRequestDto`)

```json
{
  "dmst_stex_tp": "KRX",
  "orig_ord_no": "0000140",
  "stk_cd": "005930",
  "cncl_qty": "1"
}
```

---

### 주가 DB 조회 `/api/stocks`

| Method | 엔드포인트 | 기능 | 파라미터 |
|---|---|---|---|
| GET | `/api/stocks/fetch` | API 데이터 수집 후 DB 저장 | `date` (YYYYMMDD) |
| GET | `/api/stocks/code/{srtnCd}` | 종목코드로 주가 조회 | `srtnCd` |
| GET | `/api/stocks/date/{date}` | 날짜별 주가 조회 | `date` (yyyy-MM-dd) |

---

## Known Issues

---
