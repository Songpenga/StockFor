package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomIndsInfoService extends KiwoomApiService {

    /*
    * mrkt_tp | 0:코스피, 1:코스닥, 2:코스피200
    * inds_cd | 001:종합(KOSPI), 002:대형주, 003:중형주, 004:소형주
    *           101:종합(KOSDAQ), 201:KOSPI200, 302:KOSTAR, 701: KRX100 나머지 ※ 업종코드 참고
    * */
    //업종현재가요청
    public String fn_ka20001(String mrkt_tp, String inds_cd) {
        String jsonData = String.format(
                "{\"mrkt_tp\":\"%s\",\"inds_cd\":\"%s\"}",
                mrkt_tp, inds_cd);
        return callKiwoomAPI("/api/dostk/sect", "ka20001", jsonData);
    }

    /*
    * mrkt_tp | 0:코스피, 1:코스닥, 2:코스피200
    * inds_cd | 001:종합(KOSPI), 002:대형주, 003:중형주, 004:소형주
    *           101:종합(KOSDAQ), 201:KOSPI200, 302:KOSTAR, 701: KRX100 나머지 ※ 업종코드 참고
    * */
    //업종별주가요청
    public String fn_ka20002(String mrkt_tp, String inds_cd, String stex_tp) {
        String jsonData = String.format(
                "{\"mrkt_tp\":\"%s\",\"inds_cd\":\"%s\",\"stex_tp\":\"%s\"}",
                mrkt_tp, inds_cd, stex_tp);
        return callKiwoomAPI("/api/dostk/sect", "ka20002", jsonData);
    }

    /*
    * mrkt_tp | 0:코스피, 1:코스닥, 2:코스피200
    * inds_cd | 001:종합(KOSPI), 002:대형주, 003:중형주, 004:소형주
    *           101:종합(KOSDAQ), 201:KOSPI200, 302:KOSTAR, 701: KRX100 나머지 ※ 업종코드 참고
    * */
    //업종현재가일별요청
    public String fn_ka20009(String mrkt_tp, String inds_cd) {
        String jsonData = String.format(
                "{\"mrkt_tp\":\"%s\",\"inds_cd\":\"%s\"}",
                mrkt_tp, inds_cd);
        return callKiwoomAPI("/api/dostk/sect", "ka20009", jsonData);
    }

}