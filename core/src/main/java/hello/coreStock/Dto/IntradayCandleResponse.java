package hello.coreStock.Dto;

// 틱/분봉 캔들 (dateTime: YYYYMMDDHHmmss)
public record IntradayCandleResponse(
        String dateTime,
        long open,
        long high,
        long low,
        long close,
        Long volume
) {}
