package hello.coreStock.Dto;

// 일/주/월/년봉 캔들 (date: YYYYMMDD)
public record CandleResponse(
        String date,
        long open,
        long high,
        long low,
        long close,
        Long volume
) {}
