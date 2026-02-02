package hello.coreStock.stockInterface;

import hello.coreStock.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StockPriceRepository extends JpaRepository<StockPrice, Integer> {

    //특정날짜, 특정종목 조회
    Optional<StockPrice> findByBasDtAndSrtnCd(LocalDate basDt, String srtnCd);

    // 특정 종목의 모든 데이터 조회
    List<StockPrice> findBySrtnCdOrderByBasDtDesc(String srtnCd);

    // 특정 날짜의 모든 종목 조회
    List<StockPrice> findByBasDt(LocalDate basDt);
}
