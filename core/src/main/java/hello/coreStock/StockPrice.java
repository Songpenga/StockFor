package hello.coreStock;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;

import java.time.LocalDate;

//stock_prices 테이블 매핑
@Entity
@Table(name="stock_prices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bas_dt", nullable = false)
    private LocalDate basDt; // 기준일자

    @Column(name = "srtn_cd", nullable = false, length = 20)
    private String srtnCd; // 단축코드

    @Column(name = "itms_nm", nullable = false, length = 100)
    private String itmsNm; // 종목명

    @Column(name = "clpr")
    private Integer clpr; // 종가

    @Column(name = "mkp")
    private Integer mkp; // 시가

    @Column(name = "hipr")
    private Integer hipr; // 고가

    @Column(name = "lopr")
    private Integer lopr; // 저가

    @Column(name = "trqu")
    private Long trqu; // 거래량

    @Column(name = "lstg_st_cnt")
    private Long lstgStCnt; // 상장주식수
}
