package hello.coreStock;

import hello.coreStock.Controller.StockController;
import hello.coreStock.Service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StockController.class)
public class coreStock {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private StockService stockService;

    @Test
    @DisplayName("데이터 수집 test")
    void fetchAndSaveStockData_Success() throws Exception{

        //given
        String testDate = "20260101";

        StockApiResponseDto stockApiResponseDto = new StockApiResponseDto();

        // 1
        when(stockService.StockDataGetApi(eq(testDate), isNull())).thenReturn(stockApiResponseDto);
        doNothing().when(stockService).saveStockData(testDate);

        mockMvc.perform(get("/api/stocks/fetch")
                        .param("date", testDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("데이터 수집 완료"));

        // 메서드가 실제로 호출되었는지 확인
        verify(stockService, times(1)).StockDataGetApi(testDate, null);
        verify(stockService, times(1)).saveStockData(testDate);


        //2.
        given(stockService.StockDataGetApi(eq(testDate), isNull()))
                .willReturn(stockApiResponseDto);

        doNothing().when(stockService).saveStockData(testDate);

        // when & then
        mockMvc.perform(get("/fetch")
                        .param("date", testDate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.msg").value("데이터 수집 완료"));

    }
}
