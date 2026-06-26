package hello.coreStock.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    @Value("${app.api-key}")
    private String apiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiKey = request.getHeader("X-API-KEY");
        if (apiKey.equals(requestApiKey)) {
            return true;
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":\"Invalid or missing X-API-KEY\"}");
        return false;
    }
}
