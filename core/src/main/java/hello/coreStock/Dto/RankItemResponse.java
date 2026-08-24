package hello.coreStock.Dto;

public record RankItemResponse(int rank, String code, String bareCode, String name, long currentPrice, double changeRate) {}
