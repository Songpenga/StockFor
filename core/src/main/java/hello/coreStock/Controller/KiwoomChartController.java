package hello.coreStock.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.coreStock.Dto.CandleResponse;
import hello.coreStock.Dto.IntradayCandleResponse;
import hello.coreStock.Service.KiwoomChartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static hello.coreStock.util.KiwoomValueUtils.parseVolume;

/*
* 캔들/거래량 차트 조회 (틱/분/일/주/월/년봉)
* 이동평균선은 별도 TR이 없어 close 값으로 프론트에서 계산
* */
@RestController
@RequestMapping("/api/chart")
public class KiwoomChartController {

    private static final DateTimeFormatter BASE_DT_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private KiwoomChartInfoService chartInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    // 주식일봉차트조회요청 (ka10081)
    @GetMapping("/daily/{stockCode}")
    public ResponseEntity<?> getDailyChart(
            @PathVariable String stockCode,
            @RequestParam(name = "baseDt", required = false) String baseDt,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10081(stockCode, resolveBaseDt(baseDt), updStkpcTp);
            return ResponseEntity.ok(parseCandles(result, "stk_dt_pole_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 주식주봉차트조회요청 (ka10082)
    @GetMapping("/weekly/{stockCode}")
    public ResponseEntity<?> getWeeklyChart(
            @PathVariable String stockCode,
            @RequestParam(name = "baseDt", required = false) String baseDt,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10082(stockCode, resolveBaseDt(baseDt), updStkpcTp);
            return ResponseEntity.ok(parseCandles(result, "stk_stk_pole_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 주식월봉차트조회요청 (ka10083)
    @GetMapping("/monthly/{stockCode}")
    public ResponseEntity<?> getMonthlyChart(
            @PathVariable String stockCode,
            @RequestParam(name = "baseDt", required = false) String baseDt,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10083(stockCode, resolveBaseDt(baseDt), updStkpcTp);
            return ResponseEntity.ok(parseCandles(result, "stk_mth_pole_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 주식년봉차트조회요청 (ka10094)
    @GetMapping("/yearly/{stockCode}")
    public ResponseEntity<?> getYearlyChart(
            @PathVariable String stockCode,
            @RequestParam(name = "baseDt", required = false) String baseDt,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10094(stockCode, resolveBaseDt(baseDt), updStkpcTp);
            return ResponseEntity.ok(parseCandles(result, "stk_yr_pole_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 주식분봉차트조회요청 (ka10080)
    // ticScope: 1:1분, 3:3분, 5:5분, 10:10분, 15:15분, 30:30분, 45:45분, 60:60분
    @GetMapping("/minute/{stockCode}")
    public ResponseEntity<?> getMinuteChart(
            @PathVariable String stockCode,
            @RequestParam(name = "ticScope", defaultValue = "1") String ticScope,
            @RequestParam(name = "baseDt", required = false) String baseDt,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10080(stockCode, ticScope, updStkpcTp, resolveBaseDt(baseDt));
            return ResponseEntity.ok(parseIntradayCandles(result, "stk_min_pole_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // 주식틱차트조회요청 (ka10079)
    // ticScope: 1:1틱, 3:3틱, 5:5틱, 10:10틱, 30:30틱
    @GetMapping("/tick/{stockCode}")
    public ResponseEntity<?> getTickChart(
            @PathVariable String stockCode,
            @RequestParam(name = "ticScope", defaultValue = "1") String ticScope,
            @RequestParam(name = "updStkpcTp", defaultValue = "0") String updStkpcTp) {
        try {
            String result = chartInfoService.fn_ka10079(stockCode, ticScope, updStkpcTp);
            return ResponseEntity.ok(parseIntradayCandles(result, "stk_tic_chart_qry"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    // baseDt 미지정 시 오늘 날짜로 조회 (키움 API가 요구하는 YYYYMMDD 형식)
    private String resolveBaseDt(String baseDt) {
        return (baseDt == null || baseDt.isBlank()) ? LocalDate.now().format(BASE_DT_FORMAT) : baseDt;
    }

    private List<CandleResponse> parseCandles(String result, String arrayKey) throws Exception {
        JsonNode root = objectMapper.readTree(result);
        List<CandleResponse> candles = new ArrayList<>();
        for (JsonNode item : root.get(arrayKey)) {
            candles.add(new CandleResponse(
                    item.get("dt").asText(),
                    Math.abs(Long.parseLong(item.get("open_pric").asText())),
                    Math.abs(Long.parseLong(item.get("high_pric").asText())),
                    Math.abs(Long.parseLong(item.get("low_pric").asText())),
                    Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                    parseVolume(item.get("trde_qty").asText())
            ));
        }
        return candles;
    }

    private List<IntradayCandleResponse> parseIntradayCandles(String result, String arrayKey) throws Exception {
        JsonNode root = objectMapper.readTree(result);
        List<IntradayCandleResponse> candles = new ArrayList<>();
        for (JsonNode item : root.get(arrayKey)) {
            candles.add(new IntradayCandleResponse(
                    item.get("cntr_tm").asText(),
                    Math.abs(Long.parseLong(item.get("open_pric").asText())),
                    Math.abs(Long.parseLong(item.get("high_pric").asText())),
                    Math.abs(Long.parseLong(item.get("low_pric").asText())),
                    Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                    parseVolume(item.get("trde_qty").asText())
            ));
        }
        return candles;
    }
}
