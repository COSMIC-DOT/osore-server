package com.dot.osore.config;

import com.dot.osore.domain.auth.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    final private AuthHandler authHandler;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .allowCredentials(true)
                .exposedHeaders("Custom-Header")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandler)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/api/auth/check",
                        "/api/auth/github",
                        "/api/auth/github/**",
                        "/api/auth/github/callback",
                        "/api/auth/github/callback/**"
                );
    }
}
