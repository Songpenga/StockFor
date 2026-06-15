package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service //TR 정보조회
public class KiwoomSTKInfoService extends KiwoomApiService {

    //실시간종목조회순위
    public String fn_ka00198(String queryType) {
        //1:1분, 2:10분, 3:1시간, 4:당일 누적, 5:30초
        String jsonData = String.format("{\"qry_tp\":\"%s\"}", queryType);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka00198", jsonData);
    }

    //종목정보리스트
    public String fn_ka10099(String marketCategory) {
        //시장구분
        //0 : 코스피, 2 : 인프라투융자, 3 : ELW, 4 : 뮤추얼펀드, 5 : 신주인수권, 6 : 리츠종목, 7 : 신주인수권증서, 8 : ETF, 9 : 하이일드펀드
        //10 : 코스닥, 30 : K-OTC, 50 : 코넥스, 60 : ETN, 70 : 손실제한 ETN, 80 : 금현물, 90 : 변동성 ETN
        String jsonData = String.format("{\"mrkt_tp\":\"%s\"}", marketCategory);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10099", jsonData);
    }

    //주식기본정보조회
    public String fn_ka10001(String stockCode) {
        //거래소별 종목코드
        //(KRX:039490,NXT:039490_NX,SOR:039490_AL)
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10001", jsonData);
    }

    //체결정보요청
    public String fn_ka10003(String stockCode) {
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10003", jsonData);
    }

    // 관심종목정보요청
    //   거래소별 종목코드
    //   (KRX:039490,NXT:039490_NX,SOR:039490_AL)
    //   여러개의 종목코드 입력시 | 로 구분
    public String fn_ka10095(String stockCode) {
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10095", jsonData);
    }

    /*
     * 종목정보조회
     * */
    public String fn_ka10100(String stockCode) {
        String jsonData = String.format("{\"stk_cd\":\"%s\"}", stockCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10100", jsonData);
    }

    /*
     * 업종코드 리스트
     * mrkt_tp | 시장구분 | 0:코스피(거래소),1:코스닥,2:KOSPI200,4:KOSPI100,7:KRX100(통합지수)
     * */
    public String fn_ka10101(String mrktCode) {
        String jsonData = String.format("{\"mrkt_tp\":\"%s\"}", mrktCode);
        return callKiwoomAPI("/api/dostk/stkinfo", "ka10101", jsonData);
    }

    // 거래량급증요청
    // sort_tp: 1:급증량, 2:급증률, 3:급감량, 4:급감률
    // tm_tp: 1:분, 2:전일 / tm: 분 입력 (tm_tp=1일 때 사용)
    // trde_qty_tp: 5:5천주이상, 10:만주이상, 50:5만주이상, 100:10만주이상, ...
    // stk_cnd: 0:전체조회, 1:관리종목제외, 3:우선주제외, ...
    // pric_tp: 0:전체조회, 2:5만원이상, 5:1만원이상, 6:5천원이상, 8:1천원이상, 9:10만원이상
    // stex_tp: 1:KRX, 2:NXT, 3:통합
    public String fn_ka10023(String mrktTp, String sortTp, String tmTp, String trdeQtyTp,
                              String tm, String stkCnd, String pricTp, String stexTp) {
        String jsonData = String.format(
            "{\"mrkt_tp\":\"%s\",\"sort_tp\":\"%s\",\"tm_tp\":\"%s\",\"trde_qty_tp\":\"%s\",\"tm\":\"%s\",\"stk_cnd\":\"%s\",\"pric_tp\":\"%s\",\"stex_tp\":\"%s\"}",
            mrktTp, sortTp, tmTp, trdeQtyTp, tm, stkCnd, pricTp, stexTp);
        return callKiwoomAPI("/api/dostk/rkinfo", "ka10023", jsonData);
    }

    // 전일대비등락률상위요청
    // sort_tp: 1:상승률, 2:상승폭, 3:하락률, 4:하락폭, 5:보합
    // trde_qty_cnd: 0000:전체, 0010:만주이상, 0050:5만주이상, 0100:10만주이상, ...
    // stk_cnd: 0:전체조회, 1:관리종목제외, 3:우선주제외, 14:ETF제외, ...
    // crd_cnd: 0:전체조회, 1:신용융자A군, 2:B군, 3:C군, 4:D군, 7:E군, 9:전체
    // updown_incls: 0:불포함, 1:포함
    // pric_cnd: 0:전체, 1:1천원미만, 2:1천~2천원, 3:2천~5천원, 4:5천~1만원, 5:1만원이상, 8:1천원이상, 10:1만원미만
    // trde_prica_cnd: 0:전체, 3:3천만원이상, 5:5천만원이상, 10:1억이상, ...
    // stex_tp: 1:KRX, 2:NXT, 3:통합
    public String fn_ka10027(String mrktTp, String sortTp, String trdeQtyCnd, String stkCnd,
                              String crdCnd, String updownIncls, String pricCnd, String trdePricaCnd, String stexTp) {
        String jsonData = String.format(
            "{\"mrkt_tp\":\"%s\",\"sort_tp\":\"%s\",\"trde_qty_cnd\":\"%s\",\"stk_cnd\":\"%s\",\"crd_cnd\":\"%s\",\"updown_incls\":\"%s\",\"pric_cnd\":\"%s\",\"trde_prica_cnd\":\"%s\",\"stex_tp\":\"%s\"}",
            mrktTp, sortTp, trdeQtyCnd, stkCnd, crdCnd, updownIncls, pricCnd, trdePricaCnd, stexTp);
        return callKiwoomAPI("/api/dostk/rkinfo", "ka10027", jsonData);
    }

    // 당일거래량상위요청
    // sort_tp: 1:거래량, 2:거래회전율, 3:거래대금
    // mang_stk_incls: 0:관리종목포함, 1:관리종목/우선주제외, 11:정리매매제외, ...
    // crd_tp: 0:전체조회, 9:신용융자전체, 1:A군, 2:B군, 3:C군, 4:D군, 8:신용대주
    // trde_qty_tp: 0:전체, 5:5천주이상, 10:1만주이상, 50:5만주이상, ...
    // pric_tp: 0:전체, 1:1천원미만, 5:5천원이상, 6:5천~1만원, 7:1만원이상, 8:5만원이상, ...
    // trde_prica_tp: 0:전체, 1:1천만원이상, 3:3천만원이상, 5:1억이상, ...
    // mrkt_open_tp: 0:전체, 1:장중, 2:장전시간외, 3:장후시간외
    // stex_tp: 1:KRX, 2:NXT, 3:통합
    public String fn_ka10030(String mrktTp, String sortTp, String mangStkIncls, String crdTp,
                              String trdeQtyTp, String pricTp, String trdePricaTp, String mrktOpenTp, String stexTp) {
        String jsonData = String.format(
            "{\"mrkt_tp\":\"%s\",\"sort_tp\":\"%s\",\"mang_stk_incls\":\"%s\",\"crd_tp\":\"%s\",\"trde_qty_tp\":\"%s\",\"pric_tp\":\"%s\",\"trde_prica_tp\":\"%s\",\"mrkt_open_tp\":\"%s\",\"stex_tp\":\"%s\"}",
            mrktTp, sortTp, mangStkIncls, crdTp, trdeQtyTp, pricTp, trdePricaTp, mrktOpenTp, stexTp);
        return callKiwoomAPI("/api/dostk/rkinfo", "ka10030", jsonData);
    }

    // 거래대금상위요청
    // mang_stk_incls: 0:관리종목 미포함, 1:관리종목 포함
    // stex_tp: 1:KRX, 2:NXT, 3:통합
    public String fn_ka10032(String mrktTp, String mangStkIncls, String stexTp) {
        String jsonData = String.format(
            "{\"mrkt_tp\":\"%s\",\"mang_stk_incls\":\"%s\",\"stex_tp\":\"%s\"}",
            mrktTp, mangStkIncls, stexTp);
        return callKiwoomAPI("/api/dostk/rkinfo", "ka10032", jsonData);
    }

}