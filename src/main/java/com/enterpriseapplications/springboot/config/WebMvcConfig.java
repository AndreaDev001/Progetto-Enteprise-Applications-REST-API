package com.enterpriseapplications.springboot.config;


import com.enterpriseapplications.springboot.config.interceptors.LoggingInterceptor;
import com.enterpriseapplications.springboot.config.interceptors.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer
{
    private final RateLimitInterceptor rateLimitInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor);
        registry.addInterceptor(loggingInterceptor);
    }
}
