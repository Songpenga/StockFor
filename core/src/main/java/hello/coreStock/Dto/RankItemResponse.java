package hello.coreStock.Dto;

public record RankItemResponse(int rank, String code, String name, long currentPrice, double changeRate) {}
