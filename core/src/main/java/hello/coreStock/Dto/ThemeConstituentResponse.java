package hello.coreStock.Dto;

public record ThemeConstituentResponse(
        String code,
        String bareCode,
        String name,
        long currentPrice,
        double changeAmount,
        double changeRate,
        Long volume
) {}
