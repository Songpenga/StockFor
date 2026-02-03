package hello.coreStock.Controller;

import hello.coreStock.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/token")
public class KiwoomTokenController {

    @Autowired
    private TokenService tokenService;

    /*
    * 토큰상태조회
    * */
    @GetMapping("/status")
    public ResponseEntity<?> getTokenStatus(){
        return ResponseEntity.ok(tokenService.getTokenStatus());
    }

/*    *//**
     * 수동 토큰 갱신
     *//*
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken() {
        tokenService.forceRefresh();
        return ResponseEntity.ok(Map.of("message", "토큰 갱신 완료"));
    }*/
}
