package com.dot.osore.auth.service;

import com.dot.osore.auth.constant.OAuthPlatform;
import com.dot.osore.domain.user.entity.User;
import jakarta.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private Map<String, User> sessions = new HashMap<>();

    @Value("${client.github.id}")
    private String githubClientId;

    public String getOAuthURL(OAuthPlatform platform) throws Exception {
        switch (platform) {
            case GITHUB:
                return "https://github.com/login/oauth/authorize?client_id=${clientId}";
            case GOOGLE:
                return "hello";
        }
        throw new Exception();
    }

    public void signIn(List<Cookie> cookies, OAuthPlatform platform) throws Exception {
        Cookie session = (Cookie) cookies.stream()
                .filter(c -> "JSESSIONID".equals(c.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());

        if (Objects.equals(session, null)) throw new Exception();
        String sessionId = session.getValue();

        if (sessions.containsKey(sessionId)) return;
        sessions.put(sessionId, null);
    }
}
