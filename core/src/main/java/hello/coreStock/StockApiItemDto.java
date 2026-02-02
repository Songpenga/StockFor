package hello.coreStock;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StockApiItemDto {

    @JsonProperty("basDt")
    private String basDt; // 기준일자

    @JsonProperty("srtnCd")
    private String srtnCd; // 단축코드

    @JsonProperty("itmsNm")
    private String itmsNm; // 종목명

    @JsonProperty("clpr")
    private String clpr; // 종가

    @JsonProperty("mkp")
    private String mkp; // 시가

    @JsonProperty("hipr")
    private String hipr; // 고가

    @JsonProperty("lopr")
    private String lopr; // 저가

    @JsonProperty("trqu")
    private String trqu; // 거래량

    @JsonProperty("lstgStCnt")
    private String lstgStCnt; // 상장주식수
}
