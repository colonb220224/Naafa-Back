package com.colonb.naafa.auth.oauth2.handler;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.auth.oauth2.DefaultOAuth2UserExtend;
import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        DefaultOAuth2UserExtend oAuth2User = (DefaultOAuth2UserExtend) authentication.getPrincipal();
        Optional<User> data = userMapper.findByProviderAndSocialId(oAuth2User.getProvider(), oAuth2User.getSocialId());
        if (data.isEmpty()){
            throw new RuntimeException("유저 데이터 존재하지 않음");
        }
        UserDetailsImpl userDetails = new UserDetailsImpl(data.get());
        authentication = new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Cookie jwtCookie = new Cookie("JWT", jwtTokenProvider.createToken(userDetails.getUser()));
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(10800);
        response.addCookie(jwtCookie);
        response.sendRedirect("/");
    }
}
