package com.dot.osore.core.auth.controller;

import com.dot.osore.core.auth.constant.OAuthPlatform;
import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.auth.handler.Login;
import com.dot.osore.core.auth.handler.PublicPath;
import com.dot.osore.core.auth.service.AuthService;
import com.dot.osore.global.constant.ErrorCode;
import com.dot.osore.global.response.Response;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Value("${client.url}")
    private String clientURL;

    @PublicPath
    @GetMapping("/sign-in")
    public Response signIn(HttpSession session, @RequestParam String name) {
        try {
            SignInInfo signInInfo = authService.signIn(name);
            session.setAttribute("signInInfo", signInInfo);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PublicPath
    @GetMapping("/github")
    public Response githubRedirect(HttpServletResponse response) {
        try {
            String oauthURL = authService.getOAuthURL(OAuthPlatform.GITHUB);
            response.sendRedirect(oauthURL);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PublicPath
    @GetMapping("/github/callback")
    public Response githubSignIn(HttpServletResponse response, HttpSession session, @RequestParam String code) {
        try {
            SignInInfo signInInfo = authService.signInOAuth(code, OAuthPlatform.GITHUB);
            session.setAttribute("signInInfo", signInInfo);
            response.sendRedirect(clientURL);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PublicPath
    @GetMapping("/google")
    public Response googleRedirect(HttpServletResponse response) {
        try {
            String oauthURL = authService.getOAuthURL(OAuthPlatform.GOOGLE);
            response.sendRedirect(oauthURL);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PublicPath
    @GetMapping("/google/callback")
    public Response googleSignIn(HttpServletResponse response, HttpSession session, @RequestParam String code) {
        try {
            SignInInfo signInInfo = authService.signInOAuth(code, OAuthPlatform.GOOGLE);
            session.setAttribute("signInInfo", signInInfo);
            response.sendRedirect(clientURL);
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @PublicPath
    @GetMapping("/sign-out")
    public Response signOut(HttpSession session) {
        try {
            session.invalidate();
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }

    @GetMapping("/check")
    public Response signInCheck(@Login SignInInfo signInInfo) {
        try {
            return Response.success();
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
