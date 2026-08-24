package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomChartInfoService extends KiwoomApiService {

    /*
     * tic_scope | 틱범위 | 1:1틱, 3:3틱, 5:5틱, 10:10틱, 30:30틱
     * upd_stkpc_tp | 수정주가구분 | 0 또는 1
     * */
    //주식틱차트조회요청
    public String fn_ka10079(String stkCd, String ticScope, String updStkpcTp) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"tic_scope\":\"%s\",\"upd_stkpc_tp\":\"%s\"}",
                stkCd, ticScope, updStkpcTp);
        return callKiwoomAPI("/api/dostk/chart", "ka10079", jsonData);
    }

    /*
     * tic_scope | 틱범위 | 1:1분, 3:3분, 5:5분, 10:10분, 15:15분, 30:30분, 45:45분, 60:60분
     * base_dt | 기준일자 | YYYYMMDD
     * */
    //주식분봉차트조회요청
    public String fn_ka10080(String stkCd, String ticScope, String updStkpcTp, String baseDt) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"tic_scope\":\"%s\",\"upd_stkpc_tp\":\"%s\",\"base_dt\":\"%s\"}",
                stkCd, ticScope, updStkpcTp, baseDt);
        return callKiwoomAPI("/api/dostk/chart", "ka10080", jsonData);
    }

    /*
     * base_dt | 기준일자 | YYYYMMDD
     * upd_stkpc_tp | 수정주가구분 | 0 또는 1
     * */
    //주식일봉차트조회요청
    public String fn_ka10081(String stkCd, String baseDt, String updStkpcTp) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"base_dt\":\"%s\",\"upd_stkpc_tp\":\"%s\"}",
                stkCd, baseDt, updStkpcTp);
        return callKiwoomAPI("/api/dostk/chart", "ka10081", jsonData);
    }

    //주식주봉차트조회요청
    public String fn_ka10082(String stkCd, String baseDt, String updStkpcTp) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"base_dt\":\"%s\",\"upd_stkpc_tp\":\"%s\"}",
                stkCd, baseDt, updStkpcTp);
        return callKiwoomAPI("/api/dostk/chart", "ka10082", jsonData);
    }

    //주식월봉차트조회요청
    public String fn_ka10083(String stkCd, String baseDt, String updStkpcTp) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"base_dt\":\"%s\",\"upd_stkpc_tp\":\"%s\"}",
                stkCd, baseDt, updStkpcTp);
        return callKiwoomAPI("/api/dostk/chart", "ka10083", jsonData);
    }

    //주식년봉차트조회요청
    public String fn_ka10094(String stkCd, String baseDt, String updStkpcTp) {
        String jsonData = String.format(
                "{\"stk_cd\":\"%s\",\"base_dt\":\"%s\",\"upd_stkpc_tp\":\"%s\"}",
                stkCd, baseDt, updStkpcTp);
        return callKiwoomAPI("/api/dostk/chart", "ka10094", jsonData);
    }
}
