package com.colonb.naafa.auth.oauth2;

import com.colonb.naafa.user.enums.SocialProvider;
import lombok.Getter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Map;
import java.util.Objects;

@Getter
public class DefaultOAuth2UserExtend extends DefaultOAuth2User {

    private SocialProvider provider;


    public DefaultOAuth2UserExtend(Map<String, Object> attributes, String nameAttributeKey, SocialProvider provider) {
        super(null, attributes, nameAttributeKey);
        this.provider = provider;
    }

    public String getSocialId() {
        switch (this.provider) {
            case NAVER:
                Map<String, String> naverData = super.getAttribute("response");
                return Objects.requireNonNull(naverData).get("id");
            default:
                return super.getName();
        }
    }

    public String getEmail() {
        switch (this.provider) {
            case KAKAO:
                Map<String, Object> kakaoData = super.getAttribute("kakao_account");
                if (!Objects.requireNonNull(kakaoData).containsKey("email")) {
                    throw new UsernameNotFoundException("이메일 제공 거부 상태");
                }
                return kakaoData.get("email").toString();
            case GOOGLE:
                if (!super.getAttributes().containsKey("email")) {
                    throw new UsernameNotFoundException("이메일 제공 거부 상태");
                }
                return super.getAttribute("email");
            case NAVER:
                Map<String, Object> naverData = super.getAttribute("response");
                if (!Objects.requireNonNull(naverData).containsKey("email")) {
                    throw new UsernameNotFoundException("이메일 제공 거부 상태");
                }
                return naverData.get("email").toString();
            default:
                throw new RuntimeException("미구현 상태의 OAuth2 Provider 타입");
        }
    }
}
