package hello.coreStock.Service;

import hello.coreStock.StockApiItemDto;
import hello.coreStock.StockApiResponseDto;
import hello.coreStock.StockPrice;
import hello.coreStock.stockInterface.StockPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockService {

    private final StockPriceRepository stockPriceRepository;
    private final RestTemplate restTemplate;

    @Value("${stock.api.key_in}") // application stock.api.key 값을 주입
    private String apiKey_in;

    @Value("${stock.api.key_de}") // application stock.api.key 값을 주입
    private String apiKey_de;

    @Value("${stock.api.url}")
    private String apiUrl;

    private String beginMrktTotAmt = "5900000000000";

    //String encodedLikeItmsNm = URLEncoder.encode(likeItmsNm, StandardCharsets.UTF_8);
    String encodedBeginMrktTotAmt = URLEncoder.encode(beginMrktTotAmt, StandardCharsets.UTF_8);


    //공공데이터 api 호출하여 data 가져옴
    public StockApiResponseDto StockDataGetApi(String basDt, String srtnCd){

//        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
//                .queryParam("ServiceKey", apiKey_de)
//                .queryParam("basDt", basDt) // YYYYMMDD 형식
//                .queryParam("numOfRows", "10")
//                .queryParam("pageNo", "1")
//                .queryParam("resultType", "json")
//                .build() //인코딩 방지
//                .toUriString(); // 401 로 잠시 주석

        URI uri = UriComponentsBuilder //encode 담당
                .fromHttpUrl(apiUrl)
                .queryParam("ServiceKey", apiKey_in)
                .queryParam("basDt", basDt)
                //.queryParam("likeItmsNm", encodedLikeItmsNm)
                .queryParam("beginMrktTotAmt", encodedBeginMrktTotAmt)
                .queryParam("numOfRows", 10)
                .queryParam("pageNo", 1)
                .queryParam("resultType", "json")
                .build(true)   // ⭐ 이미 인코딩된 값임을 명시
                .toUri();


        System.out.println("테스트 URL: " + uri);

        try{
            StockApiResponseDto response = restTemplate.getForObject(uri, StockApiResponseDto.class);

            log.info("API 응답 : {}", response);
            return response;
        }catch (Exception e){
            log.error("API 호출 실패 ", e);
            log.error("API 호출 error url : " + uri);
            throw new RuntimeException("주식 데이터 조회 실패", e);
        }

    }

    //특정종목 조회
    public List<StockPrice> getStockPriceByCode(String strnCd){
        return  stockPriceRepository.findBySrtnCdOrderByBasDtDesc(strnCd);
    }

    //특정날짜 조회
    public List<StockPrice> getStockPriceByDate(LocalDate basDt){
        return  stockPriceRepository.findByBasDt(basDt);
    }

    public void saveStockData(String basDt){

        StockApiResponseDto responseDto = StockDataGetApi(basDt, null);

        // Null 체크 추가
        if (responseDto == null ||
                responseDto.getResponse() == null ||
                responseDto.getResponse().getBody() == null ||
                responseDto.getResponse().getBody().getItems() == null ||
                responseDto.getResponse().getBody().getItems().getItem() == null) {
            log.warn("API 응답 데이터가 없습니다. basDt: {}", basDt);
            return;
        }

        List<StockApiItemDto> saveItems = responseDto.getResponse()
                                                .getBody()
                                                .getItems()
                                                .getItem();

        // 각 아이템을 StockPrice 엔티티로 변환하여 저장
        int savedCount = 0;
        for (StockApiItemDto itemDto : saveItems) {
            // 날짜 변환: "20260115" -> LocalDate
            LocalDate basDtLocalDate = LocalDate.parse(
                    itemDto.getBasDt(),
                    DateTimeFormatter.ofPattern("yyyyMMdd")
            );

            // 중복 체크
            Optional<StockPrice> existing = stockPriceRepository.findByBasDtAndSrtnCd(
                    basDtLocalDate,
                    itemDto.getSrtnCd()
            );

            if (existing.isEmpty()) {
                // StockPrice 엔티티 생성
                StockPrice stockPrice = StockPrice.builder()
                        .basDt(basDtLocalDate)
                        .srtnCd(itemDto.getSrtnCd())
                        .itmsNm(itemDto.getItmsNm())
                        .clpr(Integer.parseInt(itemDto.getClpr()))
                        .mkp(Integer.parseInt(itemDto.getMkp()))
                        .hipr(Integer.parseInt(itemDto.getHipr()))
                        .lopr(Integer.parseInt(itemDto.getLopr()))
                        .trqu(Long.parseLong(itemDto.getTrqu()))
                        .lstgStCnt(Long.parseLong(itemDto.getLstgStCnt()))
                        .build();

                // 저장
                stockPriceRepository.save(stockPrice);
                savedCount++;
            }
        }

        log.info("주식 데이터 저장 완료 - {}건", savedCount);
    }
}
