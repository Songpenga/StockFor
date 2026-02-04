package hello.coreStock.Dto;

import lombok.Data;

@Data
public class sellNBuyOrderRequestDto {
    private String dmst_stex_tp; //국내거래소구분
    private String stk_cd;       //종목코드
    private String ord_qty;      //주문수량
    private String ord_uv;       //주문단가
    private String trde_tp;      //매매구분
    private String cond_uv;
}
