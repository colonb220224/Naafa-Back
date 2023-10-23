package com.colonb.naafa.auth.oauth2;

import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.user.enums.SocialProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2ServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User user = new DefaultOAuth2UserService().loadUser(request);
        SocialProvider provider = SocialProvider.valueOf(request.getClientRegistration().getRegistrationId().toUpperCase());
        //google,kakao 등 구분값
        String nameKey = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        //해당 응답에서의 유저 정보를 담은 키값명 (프로퍼티스 user-name-attribute)
        Map<String, Object> attr = user.getAttributes();
        DefaultOAuth2UserExtend oAuth2User = new DefaultOAuth2UserExtend(attr, nameKey, provider);
        processOAuth2User(oAuth2User);
        return oAuth2User;
    }

    @Transactional
    protected void processOAuth2User(DefaultOAuth2UserExtend oAuth2User) {
        Optional<User> data = userMapper.findByProviderAndSocialId(oAuth2User.getProvider(), oAuth2User.getSocialId());
        if (!data.isPresent()) {
            if (userMapper.existByUsername(oAuth2User.getEmail())) {
                throw new UsernameNotFoundException("동일한 이메일의 소셜 계정 혹은 일반 계정이 존재합니다."); // 해당 예외 발생시 oauth2 에서 자동적으로 페이지에 메세지를 띄워줌
            }
            HashMap<String, Object> param = new HashMap<>();
            try {
                param.put("username", oAuth2User.getEmail());
            } catch (Exception e) {
                throw e;
            }
            param.put("provider", oAuth2User.getProvider());
            param.put("socialId", oAuth2User.getSocialId());
            userMapper.insertSocialUser(param);
        }
        //여기까지 가입로직
    }
}
