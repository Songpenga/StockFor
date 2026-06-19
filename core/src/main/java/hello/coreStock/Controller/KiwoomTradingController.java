package hello.coreStock.Controller;

import hello.coreStock.Dto.cancelOrderRequestDto;
import hello.coreStock.Dto.editOrderRequestDto;
import hello.coreStock.Dto.sellNBuyOrderRequestDto;
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

    /* 매수 주문 */
    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(@RequestBody sellNBuyOrderRequestDto request) {
        try {
            String result = tradingService.fn_kt10000(
                    request.getDmst_stex_tp(),
                    request.getStk_cd(),
                    request.getOrd_qty(),
                    request.getOrd_uv(),
                    request.getTrde_tp(),
                    request.getCond_uv()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /*
    * 매도 주문
    * */
    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody sellNBuyOrderRequestDto request) {
        try {
            String result = tradingService.fn_kt10001(
                    request.getDmst_stex_tp(),
                    request.getStk_cd(),
                    request.getOrd_qty(),
                    request.getOrd_uv(),
                    request.getTrde_tp(),
                    request.getCond_uv()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /*
    * 주문 정정
    * */
    @PostMapping("/edit")
    public ResponseEntity<?> editOrder(@RequestBody editOrderRequestDto request) {
        try {
            String result = tradingService.fn_kt10002(
                    request.getDmst_stex_tp(),
                    request.getOrig_ord_no(),
                    request.getStk_cd(),
                    request.getMdfy_qty(),
                    request.getMdfy_uv(),
                    request.getMdfy_cond_uv()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /*
     * 주문 취소
     * */
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody cancelOrderRequestDto request) {
        try {
            String result = tradingService.fn_kt10003(
                    request.getDmst_stex_tp(),
                    request.getOrig_ord_no(),
                    request.getStk_cd(),
                    request.getCncl_qty()
            );
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
