package com.dot.osore.auth.handler;

import com.dot.osore.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthHandler implements HandlerInterceptor {
    final private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Cookie[] cookies = request.getCookies();
        if (!Objects.equals(cookies, null) && authService.isExist(List.of(cookies))) return true;

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
