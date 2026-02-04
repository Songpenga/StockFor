package hello.coreStock.Service;

import org.springframework.stereotype.Service;

@Service
public class KiwoomETFInfoService extends KiwoomApiService{

    // ETF전체시세요청
    public String allETFRatePrice() {
        String jsonData = String.format(
                "{\"txon_type\" : \"0\",\"navpre\" : \"0\",\"mngmcomp\" : \"0000\",\"txon_yn\" : \"0\"," +
                        "\"trace_idex\" : \"0\",\"stex_tp\" : \"1\"}"
        );
        return callKiwoomAPI("/api/dostk/etf", "ka40004", jsonData);
    }

}
