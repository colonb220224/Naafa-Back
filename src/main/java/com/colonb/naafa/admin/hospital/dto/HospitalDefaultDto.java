package com.colonb.naafa.admin.hospital.dto;

import com.colonb.naafa.admin.hospital.enums.ChiefCategory;
import com.colonb.naafa.admin.hospital.enums.Gender;
import lombok.Getter;

@Getter
public class HospitalDefaultDto {
    // 병원 기본정보
    private String name;
    private String address;
    private String contact;
    private String coordinate;
    // 병원장 정보
    private String chiefName;
    private String licenseNumber;
    private Gender gender;
    private ChiefCategory chiefCategory;
    // 관리자 정보
    private String username;
    private String password;
    private String phone;

}
