package hello.coreStock.Controller;

import hello.coreStock.Service.KiwoomETFInfoService;
import hello.coreStock.Service.KiwoomSTKInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
* 주식 정보 조회
* */
@RestController
@RequestMapping("/api/etf")
public class KiwoomETFController {

    @Autowired
    private KiwoomETFInfoService etfInfoService;

    //ETF종목정보요청
    @PostMapping("/item-info")
    public ResponseEntity<?> EtfInfoController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40002(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF일별추이요청
    @PostMapping("/daily-history")
    public ResponseEntity<?> EtfDailyHistoryController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40003(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF전체시세요청
    @GetMapping("/market-all-price")
    public ResponseEntity<?> EtfMarketAllPriceController(
            @RequestParam(name = "txonType", defaultValue = "0") String txonType,
            @RequestParam(name = "navpre", defaultValue = "0") String navpre,
            @RequestParam(name = "mngmcomp", defaultValue = "0000") String mngmcomp,
            @RequestParam(name = "txonYn", defaultValue = "0") String txonYn,
            @RequestParam(name = "traceIdex", defaultValue = "0") String traceIdex,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = etfInfoService.fn_ka40004(txonType, navpre, mngmcomp, txonYn, traceIdex, stexTp);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF시간대별추이요청
    @PostMapping("/hourly-detail-trade")
    public ResponseEntity<?> EtfHourlyDetailTrendController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40006(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF일자별체결요청
    @PostMapping("/daily-trade")
    public ResponseEntity<?> EtfDailyTradeController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40008(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF시간대별체결요청
    @PostMapping("/hourly-trade")
    public ResponseEntity<?> EtfHourlyTrendController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40009(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //ETF시간대별추이요청
    @PostMapping("/hourly-trade")
    public ResponseEntity<?> EtfHourlyContractController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40010(stk_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
