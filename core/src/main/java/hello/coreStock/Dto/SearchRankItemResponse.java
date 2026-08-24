package hello.coreStock.Dto;

public record SearchRankItemResponse(
        int rank,
        String code,
        String bareCode,
        String name,
        long currentPrice,
        double changeRate,
        int rankChange,
        String rankChangeSign
) {}
