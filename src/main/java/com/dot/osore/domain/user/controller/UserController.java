package com.dot.osore.domain.user.controller;

import com.dot.osore.auth.service.AuthService;
import com.dot.osore.domain.user.dto.UserResponse;
import com.dot.osore.domain.user.service.UserService;
import com.dot.osore.util.constant.ErrorCode;
import com.dot.osore.util.response.Response;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;
    final private AuthService authService;

    @GetMapping("/user")
    public Response getUserInfo(HttpServletRequest request) {
        try {
            Cookie session = authService.getSession(List.of(request.getCookies()));
            Long id = authService.getUserId(session);

            UserResponse userResponse = userService.findUser(id);
            return Response.success(userResponse);
        } catch (Exception e) {
            return Response.failure(ErrorCode.MEMBER_NOT_FOUND_EXCEPTION);
        }
    }
}
