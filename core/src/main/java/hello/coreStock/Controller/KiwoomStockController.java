package hello.coreStock.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.coreStock.Dto.RankItemResponse;
import hello.coreStock.Dto.SearchRankItemResponse;
import hello.coreStock.Dto.StockBasicInfoResponse;
import hello.coreStock.Service.KiwoomSTKInfoService;
import static hello.coreStock.util.KiwoomValueUtils.parseVolume;
import static hello.coreStock.util.StockCodeUtils.bareCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 주식 정보 조회
* */
@RestController
@RequestMapping("/api/stock")
public class KiwoomStockController {

    @Autowired
    private KiwoomSTKInfoService stkInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    //실시간종목조회순위 (ka00198) -> 프론트 "검색 상위" 위젯용 SearchRankItemResponse로 변환
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking(@RequestParam(name = "queryType", defaultValue = "1") String queryType) {
        try {
            String result = stkInfoService.fn_ka00198(queryType);
            JsonNode root = objectMapper.readTree(result);

            List<SearchRankItemResponse> rankings = new ArrayList<>();
            for (JsonNode item : root.get("item_inq_rank")) {
                String rankChgText = item.get("rank_chg").asText();
                String stkCd = item.get("stk_cd").asText();
                rankings.add(new SearchRankItemResponse(
                        item.get("bigd_rank").asInt(),
                        stkCd,
                        bareCode(stkCd),
                        item.get("stk_nm").asText(),
                        Math.abs(Long.parseLong(item.get("past_curr_prc").asText())),
                        Double.parseDouble(item.get("base_comp_chgr").asText()),
                        rankChgText.isEmpty() ? 0 : Integer.parseInt(rankChgText),
                        item.get("rank_chg_sign").asText()
                ));
            }
            return ResponseEntity.ok(rankings);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //종목정보리스트
    @GetMapping("/marketList")
    public ResponseEntity<?> getMarketList(@RequestParam(name = "marketCategory", defaultValue = "0") String marketCategory) {
        try {
            String result = stkInfoService.fn_ka10099(marketCategory);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //주식기본정보조회 (ka10001) -> StockBasicInfoResponse로 변환
    @GetMapping("/basic-info/{stockCode}")
    public ResponseEntity<?> StockBasicInfoController(@PathVariable String stockCode) {
        try {
            String result = stkInfoService.fn_ka10001(stockCode);
            JsonNode root = objectMapper.readTree(result);

            Long volume = parseVolume(root.get("trde_qty").asText());

            String stkCd = root.get("stk_cd").asText();
            StockBasicInfoResponse response = new StockBasicInfoResponse(
                    stkCd,
                    bareCode(stkCd),
                    root.get("stk_nm").asText(),
                    Math.abs(Long.parseLong(root.get("cur_prc").asText())),
                    Long.parseLong(root.get("pred_pre").asText()),
                    Double.parseDouble(root.get("flu_rt").asText()),
                    Math.abs(Long.parseLong(root.get("open_pric").asText())),
                    Math.abs(Long.parseLong(root.get("high_pric").asText())),
                    Math.abs(Long.parseLong(root.get("low_pric").asText())),
                    volume
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //체결정보요청
    @GetMapping("/trade-info/{stockCode}")
    public ResponseEntity<?> StockTradeInfoController (@PathVariable String stockCode) {
        try {
            String result = stkInfoService.fn_ka10003(stockCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //종목정보조회
    @GetMapping("/item-info/{stockCode}")
    public ResponseEntity<?> StockInfoController(@PathVariable String stockCode) {
        try {
            String result = stkInfoService.fn_ka10100(stockCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 거래량급증요청 (ka10023)
    @GetMapping("/volume-surge")
    public ResponseEntity<?> getVolumeSurge(
            @RequestParam(name = "mrktTp", defaultValue = "000") String mrktTp,
            @RequestParam(name = "sortTp", defaultValue = "1") String sortTp,
            @RequestParam(name = "tmTp", defaultValue = "2") String tmTp,
            @RequestParam(name = "trdeQtyTp", defaultValue = "5") String trdeQtyTp,
            @RequestParam(name = "tm", defaultValue = "") String tm,
            @RequestParam(name = "stkCnd", defaultValue = "0") String stkCnd,
            @RequestParam(name = "pricTp", defaultValue = "0") String pricTp,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = stkInfoService.fn_ka10023(mrktTp, sortTp, tmTp, trdeQtyTp, tm, stkCnd, pricTp, stexTp);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 전일대비등락률상위요청 (ka10027)
    @GetMapping("/price-change-ranking")
    public ResponseEntity<?> getPriceChangeRanking(
            @RequestParam(name = "mrktTp", defaultValue = "000") String mrktTp,
            @RequestParam(name = "sortTp", defaultValue = "1") String sortTp,
            @RequestParam(name = "trdeQtyCnd", defaultValue = "0000") String trdeQtyCnd,
            @RequestParam(name = "stkCnd", defaultValue = "0") String stkCnd,
            @RequestParam(name = "crdCnd", defaultValue = "0") String crdCnd,
            @RequestParam(name = "updownIncls", defaultValue = "1") String updownIncls,
            @RequestParam(name = "pricCnd", defaultValue = "0") String pricCnd,
            @RequestParam(name = "trdePricaCnd", defaultValue = "0") String trdePricaCnd,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = stkInfoService.fn_ka10027(mrktTp, sortTp, trdeQtyCnd, stkCnd, crdCnd, updownIncls, pricCnd, trdePricaCnd, stexTp);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 당일거래량상위요청 (ka10030) -> 프론트 "거래량 상위" 위젯용 RankItemResponse로 변환
    @GetMapping("/volume-ranking")
    public ResponseEntity<?> getDailyVolumeRanking(
            @RequestParam(name = "mrktTp", defaultValue = "000") String mrktTp,
            @RequestParam(name = "sortTp", defaultValue = "1") String sortTp,
            @RequestParam(name = "mangStkIncls", defaultValue = "0") String mangStkIncls,
            @RequestParam(name = "crdTp", defaultValue = "0") String crdTp,
            @RequestParam(name = "trdeQtyTp", defaultValue = "0") String trdeQtyTp,
            @RequestParam(name = "pricTp", defaultValue = "0") String pricTp,
            @RequestParam(name = "trdePricaTp", defaultValue = "0") String trdePricaTp,
            @RequestParam(name = "mrktOpenTp", defaultValue = "0") String mrktOpenTp,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = stkInfoService.fn_ka10030(mrktTp, sortTp, mangStkIncls, crdTp, trdeQtyTp, pricTp, trdePricaTp, mrktOpenTp, stexTp);
            JsonNode root = objectMapper.readTree(result);

            List<RankItemResponse> rankings = new ArrayList<>();
            int rank = 1;
            for (JsonNode item : root.get("tdy_trde_qty_upper")) {
                String stkCd = item.get("stk_cd").asText();
                rankings.add(new RankItemResponse(
                        rank++,
                        stkCd,
                        bareCode(stkCd),
                        item.get("stk_nm").asText(),
                        Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                        Double.parseDouble(item.get("flu_rt").asText())
                ));
            }
            return ResponseEntity.ok(rankings);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 거래대금상위요청 (ka10032)
    @GetMapping("/trading-value-ranking")
    public ResponseEntity<?> getTradingValueRanking(
            @RequestParam(name = "mrktTp", defaultValue = "001") String mrktTp,
            @RequestParam(name = "mangStkIncls", defaultValue = "0") String mangStkIncls,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = stkInfoService.fn_ka10032(mrktTp, mangStkIncls, stexTp);
            JsonNode root = objectMapper.readTree(result);

            List<RankItemResponse> rankings = new ArrayList<>();
            for (JsonNode item : root.get("trde_prica_upper")) {
                String stkCd = item.get("stk_cd").asText();
                rankings.add(new RankItemResponse(
                        item.get("now_rank").asInt(),
                        stkCd,
                        bareCode(stkCd),
                        item.get("stk_nm").asText(),
                        Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                        Double.parseDouble(item.get("flu_rt").asText())
                ));
            }
            return ResponseEntity.ok(rankings);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 관심종목정보요청 (ka10095)
    // stockCode: 종목코드, 여러 종목은 | 로 구분 (예: 005930|039490)
    @GetMapping("/interest-info")
    public ResponseEntity<?> getInterestStockInfo(
            @RequestParam(name = "stockCode") String stockCode) {
        try {
            String result = stkInfoService.fn_ka10095(stockCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 업종코드 리스트 (ka10101)
    // mrktTp: 0:코스피, 1:코스닥, 2:KOSPI200, 4:KOSPI100, 7:KRX100
    @GetMapping("/industry-codes")
    public ResponseEntity<?> getIndustryCodes(
            @RequestParam(name = "mrktTp", defaultValue = "0") String mrktTp) {
        try {
            String result = stkInfoService.fn_ka10101(mrktTp);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
