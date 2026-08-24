package hello.coreStock.Dto;

import java.util.List;

// 계좌평가현황요청(kt00004) 응답 — 자산현황 화면용
public record AccountSummaryResponse(
        long totalAssetValue,
        long deposit,
        long todayProfitLoss,
        double todayProfitLossRate,
        List<HoldingResponse> holdings
) {}
