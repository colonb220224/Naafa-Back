package com.colonb.naafa.user.dto;

import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class RegisterDto {
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "올바르지 않은 이메일 형식입니다.")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+]).{8,16}$")
    @NotBlank(message = "올바르지 않은 패스워드 형식입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하여야 합니다.")
    private String password;
    @NotBlank(message = "전화번호는 공백이 될 수 없습니다.")
    private String phone;
    @NotNull(message = "마케팅 수신 동의 여부가 누락되었습니다.")
    private Boolean marketing;

}
