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
                return String.format("https://github.com/login/oauth/authorize?client_id=%s", githubClientId);
            case GOOGLE:
                return "hello";
        }
        throw new Exception();
    }

    public Cookie getSession(List<Cookie> cookies) {
        return (Cookie) cookies.stream()
                .filter(c -> "JSESSIONID".equals(c.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }

    public void signIn(List<Cookie> cookies, OAuthPlatform platform) throws Exception {
        Cookie session = getSession(cookies);

        if (Objects.equals(session, null)) throw new Exception();
        String sessionId = session.getValue();

        if (sessions.containsKey(sessionId)) return;
        sessions.put(sessionId, null);
    }

    public Boolean isExist(List<Cookie> cookies) {
        Cookie session = getSession(cookies);
        return sessions.containsKey(session.getValue());
    }

    public void signOut(List<Cookie> cookies) throws Exception {
        Cookie session = getSession(cookies);
        sessions.remove(session.getValue());
    }
}
