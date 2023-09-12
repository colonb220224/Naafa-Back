package com.colonb.naafa.user.dto;

import com.colonb.naafa.user.enums.PatientRelate;
import com.colonb.naafa.util.AES256Encrypt;
import lombok.Getter;

@Getter
public class PatientDto {

    private String name;

    private String socialNumber;

    private PatientRelate relate;

    public void encryptSocialNumber() throws Exception {
        this.socialNumber = AES256Encrypt.encrypt(socialNumber);
    }

}
