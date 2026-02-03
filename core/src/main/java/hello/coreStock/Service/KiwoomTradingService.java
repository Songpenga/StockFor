package hello.coreStock.Service;

/*
* 매매주문
* */
public class KiwoomTradingService extends KiwoomApiService{

    /*
    * 매수
    * */
    public String buyStock(String stockCode, int quantity, int price, String orderType) {
        String jsonData = String.format(
                "{\"stk_code\":\"%s\",\"qty\":%d,\"price\":%d,\"ord_type\":\"%s\"}",
                stockCode, quantity, price, orderType
        );
        return callKiwoomAPI("/api/dostk/order", "ka00201", jsonData);
    }

    /*
    * 매도
    * */
    public String sellStock(String stockCode, int quantity, int price, String orderType) {
        String jsonData = String.format(
                "{\"stk_code\":\"%s\",\"qty\":%d,\"price\":%d,\"ord_type\":\"%s\",\"side\":\"sell\"}",
                stockCode, quantity, price, orderType
        );
        return callKiwoomAPI("/api/dostk/order", "ka00202", jsonData);
    }

    /*
    * 취소
    * */
    public String cancelOrder(String orderNo) {
        String jsonData = String.format("{\"ord_no\":\"%s\"}", orderNo);
        return callKiwoomAPI("/api/dostk/cancel", "ka00203", jsonData);
    }
}
