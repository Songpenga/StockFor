package hello.coreStock.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.coreStock.Dto.AccountSummaryResponse;
import hello.coreStock.Dto.HoldingResponse;
import hello.coreStock.Service.KiwoomAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 계좌 자산현황 조회 — 실제 보유 자산/손익 데이터라 /api/trade/**와 동일하게 X-API-KEY로 보호 (WebMvcConfig 참고)
* */
@RestController
@RequestMapping("/api/account")
public class KiwoomAccountController {

    @Autowired
    private KiwoomAccountInfoService accountInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    // 계좌평가현황요청 (kt00004) -> 자산현황 화면용 AccountSummaryResponse로 변환
    @GetMapping("/summary")
    public ResponseEntity<?> getAccountSummary(
            @RequestParam(name = "qryTp", defaultValue = "0") String qryTp,
            @RequestParam(name = "dmstStexTp", defaultValue = "KRX") String dmstStexTp) {
        try {
            String result = accountInfoService.fn_kt00004(qryTp, dmstStexTp);
            JsonNode root = objectMapper.readTree(result);

            List<HoldingResponse> holdings = new ArrayList<>();
            for (JsonNode item : root.get("stk_acnt_evlt_prst")) {
                String rawCd = item.get("stk_cd").asText();
                String code = rawCd.length() >= 6 ? rawCd.substring(rawCd.length() - 6) : rawCd;
                holdings.add(new HoldingResponse(
                        code,
                        item.get("stk_nm").asText(),
                        Math.abs(Long.parseLong(item.get("rmnd_qty").asText())),
                        Math.abs(Long.parseLong(item.get("avg_prc").asText())),
                        Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                        Math.abs(Long.parseLong(item.get("evlt_amt").asText())),
                        Long.parseLong(item.get("pl_amt").asText()),
                        Double.parseDouble(item.get("pl_rt").asText())
                ));
            }

            AccountSummaryResponse response = new AccountSummaryResponse(
                    Long.parseLong(root.get("aset_evlt_amt").asText()),
                    Long.parseLong(root.get("entr").asText()),
                    Long.parseLong(root.get("tdy_lspft").asText()),
                    Double.parseDouble(root.get("tdy_lspft_rt").asText()),
                    holdings
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
