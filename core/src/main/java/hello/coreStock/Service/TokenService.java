package hello.coreStock.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class TokenService {
    @Value("${kiwoom.realhost}")
    private String host;

    @Value("${kiwoom.appkey}")
    private String appkey;

    @Value("${kiwoom.mykey}")
    private String mykey;

    private String accessToken;
    private LocalDateTime tokenExpireTime;


    //토큰 발급 또는 갱신
    public synchronized String getValidToken(){
        if(accessToken == null || nowNeedRefresh()){
            getAccessToken();
        }
        return accessToken;
    }

    private boolean nowNeedRefresh() {
        if(tokenExpireTime == null){
            return true;
        }
        LocalDateTime checkBeforeExpire = tokenExpireTime.minusHours(1);
        return LocalDateTime.now().isAfter(checkBeforeExpire);
    }

    //신규 토큰 발급
    private void getAccessToken() {
        try {

            String jsonData = String.format(
                    "{\"grant_type\":\"client_credentials\",\"appkey\":\"%s\",\"secretkey\":\"%s\"}",
                    appkey,
                    mykey
            );

            String response = getTokenApi(jsonData);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonTree = mapper.readTree(response); //JSON을 트리 구조로 다루기 위한 객체

            this.accessToken = jsonTree.get("token").asText();

            // [response] :: "expires_dt":"20260204094633"
            DateTimeFormatter expFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            String expires_dt = jsonTree.get("expires_dt").asText();
            this.tokenExpireTime = LocalDateTime.parse(expires_dt, expFormatter);

            System.out.println("토큰발급 성공. 만료시간 : " + tokenExpireTime);

        } catch (Exception e) {

            throw new RuntimeException("토큰 발급 실패", e);
        }
    }

    private String getTokenApi(String jsonData) throws IOException {

        // 1. 요청할 API URL
        String endpoint = "/oauth2/token";
        String urlString = host + endpoint;

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

        // 5. 응답 본문
        try (Scanner scanner = new Scanner(connection.getInputStream(), "utf-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    /**
     * 토큰 상태 조회
     */
    public Map<String, Object> getTokenStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("hasToken", accessToken != null);
        status.put("expireTime", tokenExpireTime);
        status.put("needsRefresh", nowNeedRefresh());
        return status;
    }
}
