package com.dot.osore.auth.controller;

import com.dot.osore.auth.constant.OAuthPlatform;
import com.dot.osore.auth.manager.SessionManager;
import com.dot.osore.auth.service.AuthService;
import com.dot.osore.util.constant.ErrorCode;
import com.dot.osore.util.response.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SessionManager sessionManager;

    @Value("${client.url}")
    private String clientURL;

    @GetMapping("/check")
    public Response signInCheck(HttpServletRequest request) {
        try {
            authService.isExistSession(List.of(request.getCookies()));
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/github")
    @ResponseStatus(HttpStatus.FOUND)
    public Response githubRedirect(HttpServletResponse response) {
        try {
            Cookie session = sessionManager.createSession();
            session.setPath("/");

            response.addCookie(session);
            response.addHeader("Location", authService.getOAuthURL(OAuthPlatform.GITHUB));
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/github/callback")
    @ResponseStatus(HttpStatus.FOUND)
    public Response githubSignIn(HttpServletRequest request, HttpServletResponse response, @RequestParam String code) {
        try {
            authService.signIn(code, List.of(request.getCookies()), OAuthPlatform.GITHUB);
            response.addHeader("Location", clientURL);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/sign-out")
    public Response signOut(HttpServletRequest request) {
        try {
            authService.signOut(List.of(request.getCookies()));
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
