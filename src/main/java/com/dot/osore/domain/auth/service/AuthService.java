package com.dot.osore.domain.auth.service;

import com.dot.osore.domain.auth.constant.OAuthPlatform;
import com.dot.osore.domain.auth.dto.SignInInfo;
import com.dot.osore.domain.member.entity.User;
import com.dot.osore.domain.member.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    final private UserService userService;

    @Value("${client.github.id}")
    private String githubClientId;

    @Value("${client.github.secret}")
    private String githubClientSecret;

    /**
     * 기본 로그인을 처리하는 메서드
     *
     * @param name 사용자 name
     */
    public SignInInfo signIn(String name) {
        User user = userService.findByName(name);
        if (user != null) {
            return new SignInInfo(user.getId());
        }

        Long id = userService.save(name, "default");
        return new SignInInfo(id);
    }

    /**
     * OAuth 로그인을 처리하는 메서드
     *
     * @param code     OAuth 인증 코드
     * @param platform OAuth 플랫폼
     */
    public SignInInfo signInOAuth(String code, OAuthPlatform platform) throws Exception {
        String accessToken = getGithubAccessToken(code);
        String userInfo = getUserInfo(accessToken);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = objectMapper.readValue(userInfo, Map.class);
        String name = userInfoMap.get("login");
        String avatar = userInfoMap.get("avatar_url");

        User user = userService.findByName(name);
        if (user != null) {
            return new SignInInfo(user.getId());
        }
        Long id = userService.save(name, avatar);
        return new SignInInfo(id);
    }

    private String getGithubAccessToken(String code) throws IOException {
        String urlParameters = String.format("client_id=%s&client_secret=%s&code=%s",
                URLEncoder.encode(githubClientId, "UTF-8"),
                URLEncoder.encode(githubClientSecret, "UTF-8"),
                URLEncoder.encode(code, "UTF-8"));

        URL url = new URL("https://github.com/login/oauth/access_token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(urlParameters.getBytes());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String response = br.lines().collect(Collectors.joining());
            return extractAccessToken(response);
        } finally {
            conn.disconnect();
        }
    }

    private String extractAccessToken(String jsonResponse) {
        int start = jsonResponse.indexOf("\"access_token\":\"") + 16;
        int end = jsonResponse.indexOf("\"", start);
        return jsonResponse.substring(start, end);
    }

    private String getUserInfo(String accessToken) throws IOException {
        URL url = new URL("https://api.github.com/user");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", "token " + accessToken);
        conn.setRequestProperty("User-Agent", "JavaApp");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } finally {
            conn.disconnect();
        }
    }

    /**
     * OAuth 플랫폼에 따른 URL을 반환하는 메서드
     *
     * @param platform OAuth 플랫폼
     */
    public String getOAuthURL(OAuthPlatform platform) throws Exception {
        switch (platform) {
            case GITHUB:
                return String.format("https://github.com/login/oauth/authorize?client_id=%s", githubClientId);
            case GOOGLE:
                return "hello";
        }
        throw new Exception();
    }

}
