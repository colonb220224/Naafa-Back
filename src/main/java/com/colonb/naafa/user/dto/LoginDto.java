package com.colonb.naafa.user.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class LoginDto {
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "올바르지 않은 이메일 형식입니다.")
    private String username;

    @NotBlank(message = "올바르지 않은 패스워드 형식입니다.")
    private String password;
}
