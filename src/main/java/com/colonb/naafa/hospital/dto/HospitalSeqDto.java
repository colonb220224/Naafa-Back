package com.colonb.naafa.hospital.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class HospitalSeqDto {

    @NotNull(message = "병원 고유번호가 누락되었습니다.")
    private Integer seq;
}
