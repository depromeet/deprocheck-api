package com.depromeet.deprocheck.deprocheckapi.infrastructure.config;

import com.depromeet.deprocheck.deprocheckapi.application.auth.AuthorizationService;
import com.depromeet.deprocheck.deprocheckapi.ui.converter.EnumConverterFactory;
import com.depromeet.deprocheck.deprocheckapi.ui.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthorizationService authorizationService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authorizationService))
                .addPathPatterns("/api/**")
                // health check
                .excludePathPatterns("/api/monitor/l7check")
                // login
                .excludePathPatterns("/api/login");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new EnumConverterFactory());
    }
}
