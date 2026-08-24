package hello.coreStock.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.coreStock.Dto.IndexResponse;
import hello.coreStock.Dto.IndustryRankResponse;
import hello.coreStock.Service.KiwoomIndsInfoService;
import hello.coreStock.util.KiwoomValueUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inds")
public class KiwoomIndsController {

    @Autowired
    private KiwoomIndsInfoService indsInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    //업종현재가요청 (ka20001) -> 코스피/코스닥 지수 위젯용 IndexResponse로 변환
    @PostMapping("/current-price")
    public ResponseEntity<?> indsCurrentPriceController(String mrkt_tp, String inds_cd) {
        try {
            String result = indsInfoService.fn_ka20001(mrkt_tp, inds_cd);
            JsonNode root = objectMapper.readTree(result);

            String market = switch (inds_cd) {
                case "001" -> "KOSPI";
                case "101" -> "KOSDAQ";
                default -> inds_cd;
            };

            IndexResponse response = new IndexResponse(
                    market,
                    Math.abs(Double.parseDouble(root.get("cur_prc").asText())),
                    Double.parseDouble(root.get("pred_pre").asText()),
                    Double.parseDouble(root.get("flu_rt").asText())
            );
            return ResponseEntity.ok(response);
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

    // 전업종지수요청 (ka20003) -> 업종별 당일 등락률 랭킹 위젯용 IndustryRankResponse 리스트로 변환
    // indsCd가 실제로 시장을 결정함 (mrkt_tp는 API 스펙상 필수지만 응답에 영향 없음 - indsCd로부터 유도)
    @GetMapping("/ranking")
    public ResponseEntity<?> getIndustryRanking(
            @RequestParam(name = "indsCd", defaultValue = "001") String indsCd) {
        try {
            String mrktTp = "101".equals(indsCd) ? "1" : "0";
            String result = indsInfoService.fn_ka20003(mrktTp, indsCd);
            JsonNode root = objectMapper.readTree(result);

            List<IndustryRankResponse> industries = new ArrayList<>();
            for (JsonNode item : root.get("all_inds_idex")) {
                industries.add(new IndustryRankResponse(
                        item.get("stk_cd").asText(),
                        item.get("stk_nm").asText(),
                        Math.abs(Double.parseDouble(item.get("cur_prc").asText())),
                        Double.parseDouble(item.get("pred_pre").asText()),
                        Double.parseDouble(item.get("flu_rt").asText()),
                        KiwoomValueUtils.parseVolume(item.get("trde_qty").asText()),
                        Integer.parseInt(item.get("rising").asText()),
                        Integer.parseInt(item.get("fall").asText()),
                        Integer.parseInt(item.get("flo_stk_num").asText())
                ));
            }
            return ResponseEntity.ok(industries);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
