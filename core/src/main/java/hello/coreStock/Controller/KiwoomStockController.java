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
            String result = stkInfoService.getka00198(queryType);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //실시간종목조회순위
    @GetMapping("/marketList")
    public ResponseEntity<?> getMarketList(@RequestParam(name = "marketCategory", defaultValue = "0") String marketCategory) {
        try {
            String result = stkInfoService.getka10099(marketCategory);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //주식기본정보요청
    @GetMapping("/info/{stockCode}")
    public ResponseEntity<?> getStockInfo(@PathVariable String stockCode) {
        try {
            String result = stkInfoService.getka10001(stockCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
