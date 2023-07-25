package com.springEx.boilerplate1.config;


import com.springEx.boilerplate1.config.jwt.JwtSecurityConfig;
import com.springEx.boilerplate1.config.jwt.JwtTokenProvider;
import com.springEx.boilerplate1.config.jwt.JwtAccessDeniedHandler;
import com.springEx.boilerplate1.config.jwt.JwtAuthenticationEntryPoint;

import com.springEx.boilerplate1.config.oauth.OAuth2CustomAuthenticationSuccessHandler;
import com.springEx.boilerplate1.config.oauth.OAuth2CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Component
@Configuration
public class WebSecutityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final OAuth2CustomUserService oAuth2CustomUserService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console())
//                .antMatchers("/img/**", "/css/**", "/js/**")
//                .requestMatchers("/img/**", "/css/**", "/js/**")
                ;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.httpBasic((basic)->basic.disable());
        http.csrf((csrf)->csrf.disable());
        http.formLogin((formlogin)->formlogin.disable());
        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling((exception)->exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler));
        http.authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers(mvcMatcherBuilder.pattern("/api/auth/**")).permitAll()
                    // /와 /auth/**이외의 모든 경로는 인증 해야됨.
                    .anyRequest().authenticated())
                    .oauth2Login((authlogin) -> authlogin //2auth 설정
                    .successHandler(customAuth2SuccessHandler())
                    //OAuth 2.0 Provider로부터 사용자 정보를 가져오는 엔드포인트를 지정하는 메서드
                    .userInfoEndpoint((userinfo)->userinfo
                    //OAuth 2.0 인증이 처리되는데 사용될 사용자 서비스를 지정하는 메서드
                    .userService(oAuth2CustomUserService)));

        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }
    @Bean
    public OAuth2CustomAuthenticationSuccessHandler customAuth2SuccessHandler() {
        return new OAuth2CustomAuthenticationSuccessHandler(oAuth2CustomUserService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
