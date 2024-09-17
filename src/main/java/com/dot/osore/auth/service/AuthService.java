package com.dot.osore.auth.service;

import com.dot.osore.auth.constant.OAuthPlatform;
import com.dot.osore.domain.member.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private Map<String, Long> sessions = new HashMap<>();
    final private UserService userService;

    @Value("${client.github.id}")
    private String githubClientId;

    @Value("${client.github.secret}")
    private String githubClientSecret;

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

    public Long getUserId(Cookie session) {
        return sessions.get(session.getValue());
    }

    public void signIn(String code, List<Cookie> cookies, OAuthPlatform platform) throws Exception {
        String accessToken = getAccessToken(code);
        String userInfo = getUserInfo(accessToken);

        // user 생성
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(userInfo, Map.class);

        String name = map.get("login");
        String avatar = map.get("avatar_url");
        Long id = userService.save(name, avatar);

        // session 저장
        Cookie session = getSession(cookies);

        if (Objects.equals(session, null)) throw new Exception();
        String sessionId = session.getValue();

        if (sessions.containsKey(sessionId)) return;
        sessions.put(sessionId, id);
    }

    private String getAccessToken(String code) throws Exception {
        URL url = new URL("https://github.com/login/oauth/access_token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
            bw.write(String.format("client_id=%s&client_secret=%s&code=%s", githubClientId, githubClientSecret, code));
            bw.flush();
        }

        int responseCode = conn.getResponseCode();
        String responseData = getResponse(conn, responseCode);
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(responseData, Map.class);
        String accessToken = map.get("access_token");
        return accessToken;
    }

    private String getResponse(HttpURLConnection conn, int responseCode) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (responseCode == 200) {
            try (InputStream is = conn.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    sb.append(line);
                }
            }
        }
        return sb.toString();
    }

    public String getUserInfo(String accessToken) throws IOException {
        URL url = new URL("https://api.github.com/user");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        conn.setRequestProperty("Authorization", "token " + accessToken);

        int responseCode = conn.getResponseCode();
        String response = getResponse(conn, responseCode);
        conn.disconnect();

        return response;
    }

    public Boolean isExist(List<Cookie> cookies) {
        Cookie session = getSession(cookies);
        return sessions.containsKey(session.getValue());
    }

    public void isExistSession(List<Cookie> cookies) throws Exception {
        Cookie session = getSession(cookies);
        if (!sessions.containsKey(session.getValue())) throw new Exception();
    }

    public void signOut(List<Cookie> cookies) throws Exception {
        Cookie session = getSession(cookies);
        sessions.remove(session.getValue());
    }
}
