package hello.coreStock.Service;

import org.springframework.data.repository.query.Param;
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
    public String fn_ka40004() {
        String jsonData = String.format(
                "{\"txon_type\" : \"0\",\"navpre\" : \"0\",\"mngmcomp\" : \"0000\",\"txon_yn\" : \"0\"," +
                        "\"trace_idex\" : \"0\",\"stex_tp\" : \"1\"}"
        );
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
