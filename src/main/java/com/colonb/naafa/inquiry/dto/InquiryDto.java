package com.colonb.naafa.inquiry.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class InquiryDto {

    @NotBlank(message = "제목이 누락되었습니다.")
    private String title;
    @NotBlank(message = "내용이 누락되었습니다.")
    private String contents;

}
