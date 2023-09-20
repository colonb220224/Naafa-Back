package com.colonb.naafa.inquiry.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class InquiryAnswerDto {

    @NotBlank(message = "내용이 누락되었습니다.")
    private String contents;

}
