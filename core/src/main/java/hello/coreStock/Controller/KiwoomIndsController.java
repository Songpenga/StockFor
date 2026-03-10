package hello.coreStock.Controller;

import hello.coreStock.Service.KiwoomIndsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/inds")
public class KiwoomIndsController {

    @Autowired
    private KiwoomIndsInfoService indsInfoService;

    //업종현재가요청
    @PostMapping("/current-price")
    public ResponseEntity<?> indsCurrentPriceController(String mrkt_tp, String inds_cd) {
        try {
            String result = indsInfoService.fn_ka20001(mrkt_tp, inds_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //업종별주가요청
    @PostMapping("/stock-price")
    public ResponseEntity<?> indsStockPriceController(String mrkt_tp, String inds_cd, String stex_tp) {
        try {
            String result = indsInfoService.fn_ka20002(mrkt_tp, inds_cd, stex_tp);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    //업종현재가일별요청
    @PostMapping("/daily-current-price")
    public ResponseEntity<?> indsDailyCurrentroller(String mrkt_tp, String inds_cd, String stex_tp) {
        try {
            String result = indsInfoService.fn_ka20009(mrkt_tp, inds_cd);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
