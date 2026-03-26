package com.albionhelper.helper.config;

import com.albionhelper.helper.Interceptors.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BoardWriteConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;

    public BoardWriteConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns(
                        "/board/write",
                        "/board/register",
                        "/board/registerComment",
                        "/inquire/register"
                ); // Rate Limit을 적용할 URI들
    }
}
