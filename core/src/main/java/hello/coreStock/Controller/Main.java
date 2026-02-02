package hello.coreStock.Controller;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Arrays;

// 실시간종목조회순위
public class Main {
    public static void fn_ka00198(String token, String jsonData){
        fn_ka00198(token, jsonData, "N", "");
    }
    public static void fn_ka00198(String token, String jsonData, String contYn, String nextKey) {
        try {
            // 1. 요청할 API URL
            // String host = "https://mockapi.kiwoom.com"; // 모의투자
            String host = "https://api.kiwoom.com"; // 실전투자
            String endpoint = "/api/dostk/stkinfo";
            String urlString = host + endpoint;

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 2. Header 데이터 설정
            connection.setRequestMethod("POST"); // 메서드 타입
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 컨텐츠타입
            connection.setRequestProperty("authorization", "Bearer " + token); // 접근토큰
            connection.setRequestProperty("cont-yn", contYn == null? "N": contYn); // 연속조회여부
            connection.setRequestProperty("next-key", nextKey == null? "": nextKey); // 연속조회키
            connection.setRequestProperty("api-id", "ka00198"); // TR명
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
            try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
                String responseBody = scanner.useDelimiter("\\A").next();
                System.out.println("    " + responseBody);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 1. 토큰 설정
        String MY_ACCESS_TOKEN = "사용자 AccessToken";

        // 2. 요청 데이터 JSON 문자열 생성
        String jsonData = "{\"qry_tp\" : \"1\"}"; // JSON 형식의 문자열 데이터

        // 3. API 실행
        fn_ka00198(MY_ACCESS_TOKEN, jsonData);

        // next-key, cont-yn 값이 있을 경우
        // fn_ka00198(MY_ACCESS_TOKEN, jsonData, "Y", "nextkey..");
    }
}