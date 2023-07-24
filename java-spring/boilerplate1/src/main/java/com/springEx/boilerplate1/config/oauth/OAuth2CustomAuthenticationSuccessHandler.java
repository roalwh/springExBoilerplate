package com.springEx.boilerplate1.config.oauth;
import com.springEx.boilerplate1.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@RequiredArgsConstructor
@Component
//public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
public class OAuth2CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public static final String REDIRECT_URI = "http://localhost:3000/oauth2login/callback";
    //    public static final String REDIRECT_URI = "http://localhost:3000";
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    private final OAuth2CustomUserService oAuth2CustomUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String loginEmail = (String) oAuth2User.getAttributes().get("email");
        Member member = oAuth2CustomUserService.getMemberByEmail(loginEmail);

        response.sendRedirect(UriComponentsBuilder.fromUriString(REDIRECT_URI)
                .queryParam("accessToken", member.getAccessToken())
                .queryParam("expirationTimeIn", member.getAccessTokenExpireIn())
                .queryParam("refreshToken", REFRESH_TOKEN_COOKIE_NAME)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString());
    }
}