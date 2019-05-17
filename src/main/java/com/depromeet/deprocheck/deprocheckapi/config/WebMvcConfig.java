package com.depromeet.deprocheck.deprocheckapi.config;

import com.depromeet.deprocheck.deprocheckapi.component.JwtFactory;
import com.depromeet.deprocheck.deprocheckapi.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtFactory jwtFactory;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtFactory))
                .addPathPatterns("/api/**")
                // health check
                .excludePathPatterns("/api/monitor/l7check")
                // login
                .excludePathPatterns(
                        "/api/members/login",
                        "/api/admin/login"
                );
    }
}
