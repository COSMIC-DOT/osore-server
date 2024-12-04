package com.dot.osore.core.auth.service;

import com.dot.osore.core.auth.constant.OAuthPlatform;
import com.dot.osore.core.auth.dto.SignInInfo;
import com.dot.osore.core.member.entity.Member;
import com.dot.osore.core.member.service.MemberService;
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

    final private MemberService memberService;

    @Value("${client.github.id}")
    private String githubClientId;

    @Value("${client.github.secret}")
    private String githubClientSecret;

    @Value("${client.google.id}")
    private String googleClientId;

    @Value("${client.google.secret}")
    private String googleClientSecret;

    /**
     * 기본 로그인을 처리하는 메서드
     *
     * @param name 사용자 name
     */
    public SignInInfo signIn(String name) {
        Member member = memberService.findByName(name);
        if (member != null) {
            return new SignInInfo(member.getId());
        }

        Long id = memberService.save(name, "default");
        return new SignInInfo(id);
    }

    /**
     * OAuth 로그인을 처리하는 메서드
     *
     * @param code     OAuth 인증 코드
     * @param platform OAuth 플랫폼
     */
    public SignInInfo signInOAuth(String code, OAuthPlatform platform) throws Exception {
        String accessToken = getAccessToken(code, platform);
        String userInfo = getUserInfo(accessToken, platform);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> userInfoMap = objectMapper.readValue(userInfo, Map.class);

        String name;
        String avatar;
        if (platform == OAuthPlatform.GITHUB) {
            name = userInfoMap.get("login");
            avatar = userInfoMap.get("avatar_url");
        } else {
            name = userInfoMap.get("email").split("@")[0];
            avatar = userInfoMap.get("picture");
        }

        Member member = memberService.findByName(name);
        if (member != null) {
            return new SignInInfo(member.getId());
        }
        Long id = memberService.save(name, avatar);
        return new SignInInfo(id);
    }

    private String getAccessToken(String code, OAuthPlatform platform) throws IOException {
        String urlParameters;
        URL url;

        if (platform == OAuthPlatform.GITHUB) {
            urlParameters = String.format("client_id=%s&client_secret=%s&code=%s",
                    URLEncoder.encode(githubClientId, "UTF-8"),
                    URLEncoder.encode(githubClientSecret, "UTF-8"),
                    URLEncoder.encode(code, "UTF-8"));

            url = new URL("https://github.com/login/oauth/access_token");
        } else {
            urlParameters = String.format("client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&grant_type=authorization_code",
                    URLEncoder.encode(googleClientId, "UTF-8"),
                    URLEncoder.encode(googleClientSecret, "UTF-8"),
                    URLEncoder.encode(code, "UTF-8"),
                    URLEncoder.encode("https://osore.kr/api/auth/google/callback", "UTF-8"));

            url = new URL("https://oauth2.googleapis.com/token");
        }


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
        String sanitizedResponse = jsonResponse.replaceAll("\\s+", "");
        int start = sanitizedResponse.indexOf("\"access_token\":\"") + 16;
        int end = sanitizedResponse.indexOf("\"", start);
        return sanitizedResponse.substring(start, end);
    }

    private String getUserInfo(String accessToken, OAuthPlatform platform) throws IOException {
        URL url;
        if (platform == OAuthPlatform.GITHUB) {
            url = new URL("https://api.github.com/user");
        } else {
            url = new URL("https://www.googleapis.com/oauth2/v1/userinfo");
        }

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (platform == OAuthPlatform.GITHUB) {
            conn.setRequestProperty("Authorization", "token " + accessToken);
        } else {
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        }
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
                return String.format("https://accounts.google.com/o/oauth2/auth?client_id=%s&redirect_uri=%s&response_type=code&scope=openid%%20email%%20profile",
                        googleClientId, URLEncoder.encode("https://osore.kr/api/auth/google/callback", "UTF-8"));
        }
        throw new Exception();
    }

}
