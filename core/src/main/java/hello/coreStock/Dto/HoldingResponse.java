package hello.coreStock.Dto;

// 계좌평가현황요청(kt00004)의 보유종목 1건
public record HoldingResponse(
        String code,
        String name,
        long quantity,
        long avgPrice,
        long currentPrice,
        long evaluationAmount,
        long profitLossAmount,
        double profitLossRate
) {}
