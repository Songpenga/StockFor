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

    //ETF전체시세요청 :: etf 전체 시세가 가져와짐
    @PostMapping("/market-all-price")
    public ResponseEntity<?> EtfMarketAllPriceController(String stk_cd) {
        try {
            String result = etfInfoService.fn_ka40004();
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
