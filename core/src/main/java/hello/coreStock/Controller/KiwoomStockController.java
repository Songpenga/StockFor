package hello.coreStock.Controller;

import hello.coreStock.Service.KiwoomSTKInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
* 주식 정보 조회
* */
@RestController
@RequestMapping("/api/stock")
public class KiwoomStockController {

    @Autowired
    private KiwoomSTKInfoService stkInfoService;

    //실시간종목조회순위
    @GetMapping("/ranking")
    public ResponseEntity<?> getRanking(@RequestParam(name = "queryType", defaultValue = "1") String queryType) {
        try {
            String result = stkInfoService.fn_ka00198(queryType);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //종목정보리스트
    @GetMapping("/marketList")
    public ResponseEntity<?> getMarketList(@RequestParam(name = "marketCategory", defaultValue = "0") String marketCategory) {
        try {
            String result = stkInfoService.fn_ka00199(marketCategory);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //주식기본정보조회
    @GetMapping("/basic-info/{stockCode}")
    public ResponseEntity<?> StockBasicInfoController(@PathVariable String stockCode) {
        try {
            String result = stkInfoService.fn_ka10001(stockCode);
            return ResponseEntity.ok(result);
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
            String result = stkInfoService.fn_ka00100(stockCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
