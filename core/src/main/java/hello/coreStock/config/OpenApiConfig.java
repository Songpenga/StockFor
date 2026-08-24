package hello.coreStock.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI stockForOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("StockFor API")
                        .description("키움증권 REST API 기반 국내 주식/ETF/업종 조회 API 문서")
                        .version("v1"));
    }

    // 프론트엔드에 노출할 조회 API만 포함 (주문/토큰/관리자 엔드포인트 제외)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/api/stock/**", "/api/inds/**", "/api/etf/**", "/api/theme/**", "/api/chart/**")
                .build();
    }
}
