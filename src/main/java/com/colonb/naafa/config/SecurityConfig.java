package com.colonb.naafa.config;

import com.colonb.naafa.auth.oauth2.OAuth2ServiceImpl;
import com.colonb.naafa.auth.oauth2.handler.OAuth2SuccessHandler;
import com.colonb.naafa.auth.oauth2.jwt.JwtFilterChain;
import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2ServiceImpl oAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션정책 고민할 필요있음
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers("/").permitAll() // 루트 경로에 대한 접근 허용
//                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN") // 어드민 경로에 대한 접근 허용
                .and()
                        .addFilterBefore(new JwtFilterChain(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login()
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2SuccessHandler);
        return http.build();
    }

}