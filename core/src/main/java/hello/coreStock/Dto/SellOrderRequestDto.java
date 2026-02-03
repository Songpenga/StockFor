package hello.coreStock.Dto;

import lombok.Data;

@Data
public class SellOrderRequestDto {
    private String stockCode;
    private int quantity;
    private int price;
    private String orderType;
}
