package hello.core3.res;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public class InetAddressTest {
    public void TestAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        address.getHostAddress(); //Result of 'InetAddress.getHostAddress()' is ignored

        long timestamp = System.currentTimeMillis(); // long을 반환
        //스프링에서 시간을 다룰 때는 System.currentTimeMillis() 대신 Java 8+ 의 java.time API를 사용하는 것을 권장합니다.

        Instant now = Instant.now();
        long nowInstat = now.toEpochMilli();

        LocalDateTime now2 = LocalDateTime.now();

        ZonedDateTime now3 = ZonedDateTime.now();

        Map<String, Object> processedData = new HashMap<>();
        processedData.put("clientId", "");
        processedData.put("clientType", "");
        processedData.put("timestamp", timestamp);
        processedData.put("data", "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-HMAC-SIGNATURE", "");
        headers.set("Content-Type", "application/json");
        //HttpEntity<T>. HTTP 요청이나 응답의 바디와 헤더를 하나의 객체로 캡슐화하는 클래스

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(processedData, headers);


    }
}
