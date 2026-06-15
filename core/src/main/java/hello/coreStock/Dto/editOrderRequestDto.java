package hello.coreStock.Dto;

import lombok.Data;

@Data
public class editOrderRequestDto {
    private String dmst_stex_tp;
    private String orig_ord_no;
    private String stk_cd;
    private String mdfy_qty;
    private String mdfy_uv;
    private String mdfy_cond_uv;
}
