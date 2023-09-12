package com.colonb.naafa.hospital.dto;

import com.colonb.naafa.hospital.enums.ChiefCategory;
import com.colonb.naafa.hospital.enums.Gender;
import lombok.Getter;

@Getter
public class HospitalDefaultDto {
    // 병원 기본정보
    private String name;
    private String address;
    private String contact;
//    private String coordinate;
    // 병원장 정보
    private String chiefName;
    private String licenseNumber;
    private Gender gender;
    private ChiefCategory cheifCategory;


}
