package hello.coreStock.util;

/*
* 키움 종목코드는 조회 시 거래소구분(stex_tp)에 따라 거래소 라우팅 접미사가 붙는다.
* KRX: 005930, NXT: 005930_NX, SOR(통합): 005930_AL
* */
public class StockCodeUtils {

    private StockCodeUtils() {
    }

    // 거래소 라우팅 접미사를 제거한 순수 종목코드 (즐겨찾기 등 종목 식별용)
    public static String bareCode(String code) {
        int underscoreIndex = code.indexOf('_');
        return underscoreIndex == -1 ? code : code.substring(0, underscoreIndex);
    }
}
