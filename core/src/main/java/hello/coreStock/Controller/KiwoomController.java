package hello.coreStock.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// 접근토큰 발급
@RestController
@RequestMapping("/api/kiwoom")  // 기본 경로 설정
public class KiwoomController {

    @Value("${kiwoom.mockhost}")
    private String mockhost;

    @Value("${kiwoom.appkey}")
    private String mockkey;

    @Value("${kiwoom.mykey}")
    private String mockmykey;

    // Postman으로 테스트할 엔드포인트
    @PostMapping("/token")
    public Map<String, Object> getAccessToken() {
        Map<String, Object> result = new HashMap<>();

        try {
            System.out.println("mockkey: [" + mockkey + "]");
            System.out.println("mockmykey: [" + mockmykey + "]");

            String jsonData = String.format(
                    "{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"secretkey\":\"%s\"}",
                    mockkey,
                    mockmykey
            );

            String response = fn_au10001(jsonData);

            result.put("success", true);
            result.put("response", response);

        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    public String fn_au10001(String jsonData) {
        StringBuilder responseBody = new StringBuilder();

        try {
            // 1. 요청할 API URL
            String endpoint = "/oauth2/token";
            String urlString = mockhost + endpoint;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 2. Header 데이터 설정
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setDoOutput(true);

            // 3. JSON 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 4. 응답 헤더 출력
            System.out.println("Code: "+ connection.getResponseCode());
            System.out.println("Header:");
            String[] headerKeys = {"cont-yn","next-key","api-id"};
            connection.getHeaderFields().forEach((key, value) -> {
                if(Arrays.asList(headerKeys).contains(key)){
                    System.out.println("    " + key + ": " + value.get(0));
                }
            });

            // 5. 응답 본문 출력
            System.out.println("Body:");
            InputStream errorStatus = (connection.getResponseCode() >= 400)
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            try (Scanner scanner = new Scanner(errorStatus, "utf-8")) {
                String response = scanner.useDelimiter("\\A").next();
                System.out.println("결과 여부 및 상세 내용: " + response);
                responseBody.append(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("키움 API 호출 실패", e);
        }

        return responseBody.toString();
    }
}