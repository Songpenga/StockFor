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

    //ETF전체시세요청 :: etf 전체 시세가 가져와짐
    @PostMapping("/allRatePrice")
    public ResponseEntity<?> getRanking() {
        try {
            String result = etfInfoService.allETFRatePrice();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
