package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomThemeInfoService extends KiwoomApiService {

    /*
     * qry_tp | 검색구분 | 0:전체검색, 2:종목검색
     * stk_cd | 종목코드 (qry_tp=2일 때 사용)
     * date_tp | 날짜구분 | n일전 (1~99)
     * flu_pl_amt_tp | 등락수익구분 | 1:상위기간수익률, 2:하위기간수익률, 3:상위등락률, 4:하위등락률
     * stex_tp | 거래소구분 | 1:KRX, 2:NXT, 3:통합
     * */
    //테마그룹별요청
    public String fn_ka90001(String qryTp, String stkCd, String dateTp, String fluPlAmtTp, String stexTp) {
        String jsonData = String.format(
                "{\"qry_tp\":\"%s\",\"stk_cd\":\"%s\",\"date_tp\":\"%s\",\"flu_pl_amt_tp\":\"%s\",\"stex_tp\":\"%s\"}",
                qryTp, stkCd, dateTp, fluPlAmtTp, stexTp);
        return callKiwoomAPI("/api/dostk/thme", "ka90001", jsonData);
    }

    /*
     * date_tp | 날짜구분 | 1~99
     * thema_grp_cd | 테마그룹코드
     * stex_tp | 거래소구분 | 1:KRX, 2:NXT, 3:통합
     * */
    //테마구성종목요청
    public String fn_ka90002(String dateTp, String themaGrpCd, String stexTp) {
        String jsonData = String.format(
                "{\"date_tp\":\"%s\",\"thema_grp_cd\":\"%s\",\"stex_tp\":\"%s\"}",
                dateTp, themaGrpCd, stexTp);
        return callKiwoomAPI("/api/dostk/thme", "ka90002", jsonData);
    }
}
