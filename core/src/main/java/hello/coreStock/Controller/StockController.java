package hello.coreStock.Controller;

import hello.coreStock.Service.StockService;
import hello.coreStock.StockApiResponseDto;
import hello.coreStock.StockPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;

    /**
     * API에서 데이터 가져와서 DB에 저장
     * GET /api/stocks/fetch?date=20240115
     */

    @GetMapping("/fetch")
    public ResponseEntity<Map<String, Object>> fetchAndSaveStockData(@RequestParam(name = "date") String date){//YYYYMMDD 형식

        log.info("주식 데이터 수집 시작 ", date );

        try {
            StockApiResponseDto apiResponseDto = stockService.StockDataGetApi(date, null);
            stockService.saveStockData(date);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("msg", "데이터 수집 완료");

            return ResponseEntity.ok(response);

        } catch (Exception e){
            log.error("데이터 수집 실패", e);
            Map<String, Object> erorrResponse = new HashMap<>();
            erorrResponse.put("success", false);
            erorrResponse.put("msg", "데이터 수집 실패" + e.getMessage());
            return ResponseEntity.internalServerError().body(erorrResponse);
        }
    }

    //종목코드로 조회
    @GetMapping("/code/{srtnCd}")
    public ResponseEntity<List<StockPrice>> getStockByCode(@PathVariable(name = "srtnCd") String srtnCd) {
        List<StockPrice> stockPrices = stockService.getStockPriceByCode(srtnCd);
        return ResponseEntity.ok(stockPrices);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<StockPrice>> getStockByDate(
            @PathVariable(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {  // ← name 추가!
        List<StockPrice> stocks = stockService.getStockPriceByDate(date);
        return ResponseEntity.ok(stocks);
    }

}
