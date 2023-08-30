package com.colonb.naafa.user.entity;

import com.colonb.naafa.user.enums.SocialProvider;
import com.colonb.naafa.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private long seq;
    private String username;
    private String password;
    private SocialProvider provider;
    private String socialId;
    private UserRole role;
}