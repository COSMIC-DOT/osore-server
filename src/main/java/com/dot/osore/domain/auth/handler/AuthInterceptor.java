package com.dot.osore.domain.auth.handler;

import com.dot.osore.domain.auth.dto.AuthContext;
import com.dot.osore.domain.auth.dto.SignInInfo;
import com.dot.osore.util.constant.ErrorCode;
import com.dot.osore.util.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/h2-console")) {
            return true;
        }

        if (preProcessing(handler, request)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (isPublicPath(handlerMethod)) {
            return true;
        }

        HttpSession session = request.getSession();
        Object attribute = session.getAttribute("signInInfo");
        if (Objects.isNull(attribute)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Response failureResponse = Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
            String jsonResponse = new ObjectMapper().writeValueAsString(failureResponse);

            response.getWriter().write(jsonResponse);
            return false;
        }

        SignInInfo signInInfo = (SignInInfo) attribute;
        authContext.setPrincipal(signInInfo);
        return true;
    }

    private boolean preProcessing(final Object handler, final HttpServletRequest request) {
        return handler instanceof ResourceHttpRequestHandler || CorsUtils.isPreFlightRequest(request);
    }

    private boolean isPublicPath(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(PublicPath.class) || handlerMethod.getBeanType().isAnnotationPresent(PublicPath.class);
    }
}
