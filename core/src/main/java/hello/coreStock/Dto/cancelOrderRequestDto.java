package hello.coreStock.Dto;

import lombok.Data;

@Data
public class cancelOrderRequestDto {
    private String dmst_stex_tp;
    private String orig_ord_no;
    private String stk_cd;
    private String cncl_qty;
}
