package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service
public class KiwoomETFInfoService extends KiwoomApiService{

    //ETF종목정보요청
    public String fn_ka40002(String stk_cd) {

        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40002", jsonData);
    }

    //ETF일별추이요청
    public String fn_ka40003(String stk_cd) {

        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40003", jsonData);
    }

    // ETF전체시세요청
    // txon_type: 과세유형, navpre: NAV대비괴리, mngmcomp: 운용사코드(0000:전체)
    // txon_yn: 과세여부, trace_idex: 추적지수, stex_tp: 1:KRX, 2:NXT, 3:통합
    public String fn_ka40004(String txonType, String navpre, String mngmcomp,
                              String txonYn, String traceIdex, String stexTp) {
        String jsonData = String.format(
                "{\"txon_type\":\"%s\",\"navpre\":\"%s\",\"mngmcomp\":\"%s\"," +
                "\"txon_yn\":\"%s\",\"trace_idex\":\"%s\",\"stex_tp\":\"%s\"}",
                txonType, navpre, mngmcomp, txonYn, traceIdex, stexTp);
        return callKiwoomAPI("/api/dostk/etf", "ka40004", jsonData);
    }

    // ETF시간대별추이요청
    public String fn_ka40006(String stk_cd) {
        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40006", jsonData);
    }

    // ETF시간대별체결요청
    public String fn_ka40007(String stk_cd) {
        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40007", jsonData);
    }

    // ETF일자별체결요청
    public String fn_ka40008(String stk_cd) {
        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40008", jsonData);
    }

    // ETF시간대별체결요청
    public String fn_ka40009(String stk_cd) {
        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40009", jsonData);
    }

    //ETF시간대별추이요청
    public String fn_ka40010(String stk_cd) {
        String jsonData = String.format(
                "{\"stk_cd\" : \"%s\"}", stk_cd );
        return callKiwoomAPI("/api/dostk/etf", "ka40010", jsonData);
    }
}
