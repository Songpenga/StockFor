package hello.coreStock.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.coreStock.Dto.ThemeConstituentResponse;
import hello.coreStock.Dto.ThemeGroupResponse;
import hello.coreStock.Service.KiwoomThemeInfoService;
import hello.coreStock.util.KiwoomValueUtils;
import hello.coreStock.util.StockCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 테마 정보 조회
* */
@RestController
@RequestMapping("/api/theme")
public class KiwoomThemeController {

    @Autowired
    private KiwoomThemeInfoService themeInfoService;

    @Autowired
    private ObjectMapper objectMapper;

    // 테마그룹별요청 (ka90001) -> 프론트 "인기 업종·테마" 위젯용 ThemeGroupResponse로 변환
    @GetMapping("/groups")
    public ResponseEntity<?> getThemeGroups(
            @RequestParam(name = "qryTp", defaultValue = "0") String qryTp,
            @RequestParam(name = "stkCd", defaultValue = "") String stkCd,
            @RequestParam(name = "dateTp", defaultValue = "1") String dateTp,
            @RequestParam(name = "fluPlAmtTp", defaultValue = "3") String fluPlAmtTp,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = themeInfoService.fn_ka90001(qryTp, stkCd, dateTp, fluPlAmtTp, stexTp);
            JsonNode root = objectMapper.readTree(result);

            List<ThemeGroupResponse> themes = new ArrayList<>();
            int rank = 1;
            for (JsonNode item : root.get("thema_grp")) {
                themes.add(new ThemeGroupResponse(
                        rank++,
                        item.get("thema_grp_cd").asText(),
                        item.get("thema_nm").asText(),
                        Integer.parseInt(item.get("stk_num").asText()),
                        Double.parseDouble(item.get("flu_rt").asText()),
                        Integer.parseInt(item.get("rising_stk_num").asText()),
                        Integer.parseInt(item.get("fall_stk_num").asText()),
                        Double.parseDouble(item.get("dt_prft_rt").asText()),
                        item.get("main_stk").asText()
                ));
            }
            return ResponseEntity.ok(themes);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 테마구성종목요청 (ka90002) -> 테마 카드 클릭 시 구성 종목 목록
    @GetMapping("/{themaGrpCd}/stocks")
    public ResponseEntity<?> getThemeConstituents(
            @PathVariable String themaGrpCd,
            @RequestParam(name = "dateTp", defaultValue = "1") String dateTp,
            @RequestParam(name = "stexTp", defaultValue = "3") String stexTp) {
        try {
            String result = themeInfoService.fn_ka90002(dateTp, themaGrpCd, stexTp);
            JsonNode root = objectMapper.readTree(result);

            List<ThemeConstituentResponse> stocks = new ArrayList<>();
            for (JsonNode item : root.get("thema_comp_stk")) {
                String stkCd = item.get("stk_cd").asText();
                stocks.add(new ThemeConstituentResponse(
                        stkCd,
                        StockCodeUtils.bareCode(stkCd),
                        item.get("stk_nm").asText(),
                        Math.abs(Long.parseLong(item.get("cur_prc").asText())),
                        Double.parseDouble(item.get("pred_pre").asText()),
                        Double.parseDouble(item.get("flu_rt").asText()),
                        KiwoomValueUtils.parseVolume(item.get("acc_trde_qty").asText())
                ));
            }
            return ResponseEntity.ok(stocks);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
