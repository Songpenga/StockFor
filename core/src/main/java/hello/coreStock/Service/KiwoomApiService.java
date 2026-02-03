package hello.coreStock.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
* 키움 API 공통 호출 로직
* */
public class KiwoomApiService {
    @Autowired
    private TokenService tokenService;

    @Value("${kiwoom.mockhost}")
    private String host;

    /*
    * 키움 api 공통 호출 메서드
    * */

    protected String callKiwoomAPI(String endpoint, String apiId, String jsonData) {
        return callKiwoomAPI(endpoint, apiId, jsonData, "N", "");
    }

    protected String callKiwoomAPI(String endpoint, String apiId, String jsonData,
                                   String contYn, String nextKey) {
        try {
            // 유효한 토큰 자동 획득
            String token = tokenService.getValidToken();

            URL url = new URL(host + endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Header 설정
            connection.setRequestMethod("POST");        // 메서드 타입
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 컨텐츠타입
            connection.setRequestProperty("authorization", "Bearer " + token); // 접근토큰
            connection.setRequestProperty("cont-yn", contYn);    // 연속조회여부
            connection.setRequestProperty("next-key", nextKey);  // 연속조회키
            connection.setRequestProperty("api-id", apiId);      // TR명
            connection.setDoOutput(true);

            // Body 전송
            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonData.getBytes(StandardCharsets.UTF_8));
            }

            // 응답 처리
            int responseCode = connection.getResponseCode();
            if (responseCode >= 400) {
                try (Scanner scanner = new Scanner(connection.getErrorStream(), "utf-8")) {
                    String error = scanner.useDelimiter("\\A").next();
                    throw new RuntimeException("API 호출 실패: " + error);
                }
            }

            try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
                return scanner.useDelimiter("\\A").next();
            }

        } catch (Exception e) {
            throw new RuntimeException("키움 API 호출 실패", e);
        }
    }
}
