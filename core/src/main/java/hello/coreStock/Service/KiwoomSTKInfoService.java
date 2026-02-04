package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomSTKInfoService extends KiwoomApiService {
    /*
     * 실시간종목조회순위
     * */
    public String getka00198(String queryType) {
        //1:1분, 2:10분, 3:1시간, 4:당일 누적, 5:30초
        String jsonData = String.format("{\"qry_tp\":\"%s\"}", queryType);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka00198", jsonData);
    }

    /*
     * 종목정보리스트
     * */
    public String getka10099(String marketCategory) {
        //시장구분
        //0 : 코스피, 2 : 인프라투융자, 3 : ELW, 4 : 뮤추얼펀드, 5 : 신주인수권, 6 : 리츠종목, 7 : 신주인수권증서, 8 : ETF, 9 : 하이일드펀드
        //10 : 코스닥, 30 : K-OTC, 50 : 코넥스, 60 : ETN, 70 : 손실제한 ETN, 80 : 금현물, 90 : 변동성 ETN
        String jsonData = String.format("{\"mrkt_tp\":\"%s\"}", marketCategory);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10099", jsonData);
    }

    /*
     * 주식기본정보조회
     * */
    public String getka10001(String stockCode) {
        //거래소별 종목코드
        //(KRX:039490,NXT:039490_NX,SOR:039490_AL)
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10001", jsonData);
    }

    /*
     * 종목정보조회
     * */
    public String getka10100(String stockCode) {
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10100", jsonData);
    }

}