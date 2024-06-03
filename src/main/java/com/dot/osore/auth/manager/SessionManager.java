package com.dot.osore.auth.manager;

import jakarta.servlet.http.Cookie;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
    public Cookie createSession() {
        String sessionId = UUID.randomUUID().toString();
        return new Cookie("JSESSIONID", sessionId);
    }
}
