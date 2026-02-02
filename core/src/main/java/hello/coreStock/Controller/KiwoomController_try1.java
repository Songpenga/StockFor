package hello.coreStock.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Arrays;

// 접근토큰 발급
@RestController
@RequestMapping("/api/kiwoom2")  // 기본 경로 설정
public class KiwoomController_try1 {

    @Value("${kiwoom.mockhost}")
    private String mockhost;

    @Value("${kiwoom.appkey}")
    private String mockkey;

    @Value("${kiwoom.mykey}")
    private String mockmykey;

    public void fn_au10001_1(String jsonData) {

        try {
            // 1. 요청할 API URL
             String host = "https://mockapi.kiwoom.com"; // 모의투자
            // String host = "https://api.kiwoom.com"; // 실전투자
            String endpoint = "/oauth2/token";
            String urlString = host + endpoint;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 2. Header 데이터 설정
            connection.setRequestMethod("POST"); // 메서드 타입
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 컨텐츠타입
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
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println("결과 여부 및 상세 내용" + responseBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/token2")
    public void executeTokenRequest1() {
        System.out.println("mockkey: [" + mockkey + "]");
        System.out.println("mockmykey: [" + mockmykey + "]");

        String jsonData = String.format(
                "{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"secretkey\":\"%s\"}",
                mockkey,
                mockmykey
        );

        fn_au10001_1(jsonData);
    }
}