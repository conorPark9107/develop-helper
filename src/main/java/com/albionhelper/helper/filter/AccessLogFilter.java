package com.albionhelper.helper.filter;

import com.albionhelper.helper.domain.log.AccessLog;
import com.albionhelper.helper.admin.AccessLogRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.time.LocalDateTime;

@Component
public class AccessLogFilter extends OncePerRequestFilter {

    private final AccessLogRepository accessLogRepository;

    public AccessLogFilter(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getRequestURI().equals("/")){
            String ipAddress = request.getRemoteAddr();
            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            if(inetAddress instanceof Inet6Address){
                ipAddress = convertIPv4(ipAddress); // IPv4로 변환
            }else{
                ipAddress = inetAddress.getHostAddress();
            }

            // 접속자 정보 저장.
            AccessLog accessLog = new AccessLog(null, ipAddress, LocalDateTime.now());
            accessLogRepository.save(accessLog);

        }
        filterChain.doFilter(request, response);
    }

    private String convertIPv4(String inetAddress) {
        if (inetAddress.startsWith("::ffff:")) {
            return inetAddress.substring(7);
        }
        return inetAddress;
    }
}
