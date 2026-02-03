package hello.coreStock.Controller;

import hello.coreStock.Dto.BuyOrderRequestDto;
import hello.coreStock.Dto.SellOrderRequestDto;
import hello.coreStock.Service.KiwoomTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/trade")
public class KiwoomTradingController {

    @Autowired
    private KiwoomTradingService tradingService;

    /**
     * 매수 주문
     */
    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(@RequestBody BuyOrderRequestDto request) {
        try {
            String result = tradingService.buyStock(
                    request.getStockCode(),
                    request.getQuantity(),
                    request.getPrice(),
                    request.getOrderType()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 매도 주문
     */
    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody SellOrderRequestDto request) {
        try {
            String result = tradingService.sellStock(
                    request.getStockCode(),
                    request.getQuantity(),
                    request.getPrice(),
                    request.getOrderType()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * 주문 취소
     */
    @DeleteMapping("/order/{orderNo}")
    public ResponseEntity<?> cancelOrder(@PathVariable String orderNo) {
        try {
            String result = tradingService.cancelOrder(orderNo);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
