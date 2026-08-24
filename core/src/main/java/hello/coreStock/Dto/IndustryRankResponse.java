package hello.coreStock.Dto;

public record IndustryRankResponse(
        String industryCode,
        String industryName,
        double indexValue,
        double changeValue,
        double changeRate,
        Long volume,
        int risingStockCount,
        int fallingStockCount,
        int stockCount
) {}
