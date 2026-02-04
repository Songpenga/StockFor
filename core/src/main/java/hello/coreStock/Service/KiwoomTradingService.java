package hello.coreStock.Service;

import org.springframework.stereotype.Service;

/*
* 매매주문
* */
@Service
public class KiwoomTradingService extends KiwoomApiService{

    /*
    * 매수
    * */
    public String buyStock(String dmst_stex_tp, String stk_cd, String ord_qty, String ord_uv, String trde_tp, String cond_uv) {
        String jsonData = String.format(
                "{\"dmst_stex_tp\":\"%s\",\"stk_cd\":\"%s\",\"ord_qty\":\"%s\",\"ord_uv\":\"%s\",\"trde_tp\":\"%s\",\"cond_uv\":\"%s\"}",
                dmst_stex_tp, stk_cd, ord_qty, ord_uv, trde_tp, cond_uv
        );
        return callKiwoomAPI("/api/dostk/ordr", "kt10000", jsonData);
    }

    /*
    * 매도
    * */
    public String sellStock(String dmst_stex_tp, String stk_cd, String ord_qty, String ord_uv, String trde_tp, String cond_uv) {
        String jsonData = String.format(
                "{\"dmst_stex_tp\":\"%s\",\"stk_cd\":\"%s\",\"ord_qty\":\"%s\",\"ord_uv\":\"%s\",\"trde_tp\":\"%s\",\"cond_uv\":\"%s\"}",
                dmst_stex_tp, stk_cd, ord_qty, ord_uv, trde_tp, cond_uv
        );
        return callKiwoomAPI("/api/dostk/ordr", "kt10001", jsonData);
    }

    /*
     * 주문정정
     * */
    public String editOrder(String dmst_stex_tp, String orig_ord_no, String stk_cd, String mdfy_qty, String mdfy_uv, String mdfy_cond_uv) {
        String jsonData = String.format(
                "{\"dmst_stex_tp\":\"%s\",\"orig_ord_no\":\"%s\",\"stk_cd\":\"%s\",\"mdfy_qty\":\"%s\",\"mdfy_uv\":\"%s\",\"mdfy_cond_uv\":\"%s\"}",
                dmst_stex_tp, orig_ord_no, stk_cd, mdfy_qty, mdfy_uv, mdfy_cond_uv
        );
        return callKiwoomAPI("/api/dostk/ordr", "kt10003", jsonData);
    }

    /*
    * 취소
    * */
    public String cancelOrder(String dmst_stex_tp, String orig_ord_no, String stk_cd, String cncl_qty) {
        String jsonData = String.format(
                "{\"dmst_stex_tp\":\"%s\",\"orig_ord_no\":\"%s\",\"stk_cd\":\"%s\",\"cncl_qty\":\"%s\"}",
                dmst_stex_tp, orig_ord_no, stk_cd, cncl_qty
        );

        return callKiwoomAPI("/api/dostk/ordr", "kt10003", jsonData);
    }
}
