package hello.coreStock.Dto;

public record ThemeGroupResponse(
        int rank,
        String themeCode,
        String themeName,
        int stockCount,
        double changeRate,
        int risingStockCount,
        int fallingStockCount,
        double periodReturnRate,
        String mainStock
) {}
