package hello.coreStock.Dto;

public record StockBasicInfoResponse(
        String code,
        String bareCode,
        String name,
        long currentPrice,
        long changeAmount,
        double changeRate,
        long openPrice,
        long highPrice,
        long lowPrice,
        Long volume
) {}
