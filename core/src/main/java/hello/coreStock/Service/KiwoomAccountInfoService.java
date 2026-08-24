package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomAccountInfoService extends KiwoomApiService {

    /*
     * qry_tp | 상장폐지조회구분 | 0:전체, 1:상장폐지종목제외
     * dmst_stex_tp | 국내거래소구분 | KRX:한국거래소, NXT:넥스트트레이드
     * */
    //계좌평가현황요청
    public String fn_kt00004(String qryTp, String dmstStexTp) {
        String jsonData = String.format(
                "{\"qry_tp\":\"%s\",\"dmst_stex_tp\":\"%s\"}",
                qryTp, dmstStexTp);
        return callKiwoomAPI("/api/dostk/acnt", "kt00004", jsonData);
    }
}
