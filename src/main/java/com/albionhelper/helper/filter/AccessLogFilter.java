package com.albionhelper.helper.filter;

import com.albionhelper.helper.admin.AccessLogRepository;
import com.albionhelper.helper.domain.log.AccessLog;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccessLogFilter extends OncePerRequestFilter {

    private final AccessLogRepository accessLogRepository;
    private final Map<String, Long> lastAccessMap = new ConcurrentHashMap<>();

    public AccessLogFilter(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/")) {
            String ipAddress = getClientIP(request);

            // 접속자 정보 저장.
            if (isAllowed(ipAddress)) {
                accessLogRepository.save(new AccessLog(null, ipAddress, LocalDateTime.now()));
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");

        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }

        // 여러 IP가 있을 경우 첫 번째가 실제 클라이언트
        return xfHeader.split(",")[0];
    }

    //
    private boolean isAllowed(String ip) {
        long now = System.currentTimeMillis();
        Long last = lastAccessMap.get(ip);

        if (last != null && (now - last) < 60000) { // 1분 제한
            return false;
        }

        lastAccessMap.put(ip, now);
        return true;
    }
}
