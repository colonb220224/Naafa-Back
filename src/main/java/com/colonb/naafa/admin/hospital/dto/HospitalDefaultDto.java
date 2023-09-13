package com.colonb.naafa.admin.hospital.dto;

import com.colonb.naafa.admin.hospital.enums.ChiefCategory;
import com.colonb.naafa.admin.hospital.enums.Gender;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class HospitalDefaultDto {
    // 병원 기본정보
    @NotBlank(message = "병원명이 누락되었습니다.")
    private String name;
    @NotBlank(message = "병원주소가 누락되었습니다.")
    private String address;
    @NotBlank(message = "대표연락처가 누락되었습니다.")
    private String contact;
    @NotBlank(message = "병원좌표가 누락되었습니다.")
    private String coordinate;
    // 병원장 정보
    @NotBlank(message = "대표원장명이 누락되었습니다.")
    private String chiefName;
    @NotBlank(message = "의사면허번호가 누락되었습니다.")
    private String licenseNumber;
    @NotNull(message = "원장성별이 누락되었습니다.")
    private Gender gender;
    @NotNull(message = "원장의사분류가 누락되었습니다.")
    private ChiefCategory chiefCategory;
    // 관리자 정보
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "올바르지 않은 이메일 형식입니다.")
    private String username;
    @Pattern(regexp ="^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+]).{8,16}$")
    @NotBlank(message = "올바르지 않은 패스워드 형식입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하여야 합니다.")
    private String password;
    @NotBlank(message = "전화번호는 공백이 될 수 없습니다.")
    private String phone;

}
