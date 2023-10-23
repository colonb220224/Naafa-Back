package com.colonb.naafa.user.dto;

import com.colonb.naafa.user.enums.PatientRelate;
import com.colonb.naafa.util.AES256Encrypt;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class PatientDto {

    @NotBlank(message = "이름이 누락되었습니다.")
    private String name;

    @NotBlank(message = "주민등록번호가 누락되었습니다.")
    private String socialNumber;

    @NotNull(message = "본인과의 관계가 누락되었습니다.")
    private PatientRelate relate;


    public void encryptSocialNumber() throws Exception {
        this.socialNumber = AES256Encrypt.encrypt(socialNumber);
    }

}
