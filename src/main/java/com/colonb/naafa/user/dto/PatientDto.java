package com.colonb.naafa.user.dto;

import com.colonb.naafa.user.enums.PatientRelate;
import lombok.Getter;

@Getter
public class PatientDto {

    private String name;

    private String socialNumber;

    private PatientRelate relate;

}
