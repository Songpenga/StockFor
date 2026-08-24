package hello.coreStock.Dto;

public record IndexResponse(String market, double indexValue, double changeValue, double changeRate) {}
