package com.colonb.naafa.admin.hospital.dto;

import com.colonb.naafa.hospital.enums.ChiefCategory;
import com.colonb.naafa.hospital.enums.Gender;
import lombok.Getter;

import javax.validation.constraints.*;

@Getter
public class HospitalDto {
    @NotBlank(message = "병원명이 누락되었습니다.")
    private String name;
    @NotBlank(message = "병원주소가 누락되었습니다.")
    private String address;
    @NotBlank(message = "대표연락처가 누락되었습니다.")
    private String contact;
    @NotBlank(message = "병원좌표가 누락되었습니다.")
    private String coordinate;
    @NotBlank(message = "대표원장명이 누락되었습니다.")
    private String chiefName;
    @NotBlank(message = "의사면허번호가 누락되었습니다.")
    private String licenseNumber;
    @NotNull(message = "원장성별이 누락되었습니다.")
    private Gender gender;
    @NotNull(message = "원장의사분류가 누락되었습니다.")
    private ChiefCategory chiefCategory;
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    @NotBlank(message = "올바르지 않은 이메일 형식입니다.")
    private String username;
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()_+]).{8,16}$")
    //간편비밀번호사용시 정규식 수정필요
    @NotBlank(message = "올바르지 않은 패스워드 형식입니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 8자이상 16자 이하여야 합니다.")
    private String password;
    @NotBlank(message = "전화번호는 공백이 될 수 없습니다.")
    private String phone;
    // 병원 INFO 정보(treatment, DETAILS, PARKING)
    @NotBlank(message = "병원소개가 누락되었습니다.")
    private String treatment;
    @NotBlank(message = "병원 상세소개가 누락되었습니다.")
    private String details;
    @NotBlank(message = "병원 주차정보가 누락되었습니다.")
    private String parking;

    @NotNull(message = "방문 예약 가능 여부가 누락되었습니다.")
    private Boolean visitReserveEnable;
    @NotNull(message = "방문 예약 운영 시간이 누락되었습니다.")
    private Integer visitReserveStartAt;
    @NotNull(message = "방문 예약 운영 시간이 누락되었습니다.")
    private Integer visitReserveEndAt;
    @NotNull(message = "시간당 방문 예약 가능 인원 수가 누락되었습니다.")
    private Integer visitReserveRerHour;
    @NotBlank(message = "방문 예약 취소 가능 시간이 누락되었습니다.")
    private String visitReserveCancellationPeriod;
    @NotNull(message = "방문 예약 주말 가능 여부가 누락되었습니다.")
    private Boolean visitReserveWeekend;

    @NotNull(message = "비대면 상담 가능 여부가 누락되었습니다.")
    private Boolean consultationReserveEnable;
    @NotNull(message = "비대면 상담 운영 시간이 누락되었습니다.")
    private Integer consultationReserveStartAt;
    @NotNull(message = "비대면 상담 운영 시간이 누락되었습니다.")
    private Integer consultationReserveEndAt;
    @NotNull(message = "시간당 비대면 상담 가능 인원 수가 누락되었습니다.")
    private Integer consultationReserveRerHour;
    @NotBlank(message = "비대면 상담 취소 가능 시간이 누락되었습니다.")
    private String consultationReserveCancellationPeriod;
    @NotNull(message = "비대면 상담 주말 가능 여부가 누락되었습니다.")
    private Boolean consultationReserveWeekend;

    @NotNull(message = "온라인 접수 가능 여부가 누락되었습니다.")
    private Boolean onlineReserveEnable;
    @NotNull(message = "온라인 접수 운영 시간이 누락되었습니다.")
    private Integer onlineReserveStartAt;
    @NotNull(message = "온라인 접수 운영 시간이 누락되었습니다.")
    private Integer onlineReserveEndAt;
    @NotNull(message = "시간당 온라인 접수 가능 인원 수가 누락되었습니다.")
    private Integer onlineReserveRerHour;
    @NotBlank(message = "온라인 접수 취소 가능 시간이 누락되었습니다.")
    private String onlineReserveCancellationPeriod;
    @NotNull(message = "온라인 접수 주말 가능 여부가 누락되었습니다.")
    private Boolean onlineReserveWeekend;

    @NotNull(message = "왕진 예약 가능 여부가 누락되었습니다.")
    private Boolean houseCallReserveEnable;
    @NotNull(message = "왕진 예약 운영 시간이 누락되었습니다.")
    private Integer houseCallReserveStartAt;
    @NotNull(message = "왕진 예약 운영 시간이 누락되었습니다.")
    private Integer houseCallReserveEndAt;
    @NotNull(message = "시간당 왕진 예약 가능 인원 수가 누락되었습니다.")
    private Integer houseCallReserveRerHour;
    @NotBlank(message = "왕진 예약 취소 가능 시간이 누락되었습니다.")
    private String houseCallReserveCancellationPeriod;
    @NotNull(message = "왕진 예약 주말 가능 여부가 누락되었습니다.")
    private Boolean houseCallReserveWeekend;

    @NotNull(message = "월요일 운영여부가 누락되었습니다.")
    private Boolean mondayOperatingEnable;
    @NotNull(message = "월요일 운영 시작시간이 누락되었습니다.")
    private Integer mondayOperatingStartAt;
    @NotNull(message = "월요일 운영 마감시간이 누락되었습니다.")
    private Integer mondayOperatingEndAt;
    @NotNull(message = "월요일 점심시간 시작이 누락되었습니다.")
    private Integer mondayBreakStartAt;
    @NotNull(message = "월요일 점심시간 마감이 누락되었습니다.")
    private Integer mondayBreakEndAt;
    @NotNull(message = "월요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean mondayNightCareEnable;
    @NotNull(message = "월요일 야간진료 시작시간이 누락되었습니다.")
    private Integer mondayNightCareStartAt;
    @NotNull(message = "월요일 야간진료 마감시간이 누락되었습니다.")
    private Integer mondayNightCareEndAt;

    @NotNull(message = "화요일 운영여부가 누락되었습니다.")
    private Boolean tuesdayOperatingEnable;
    @NotNull(message = "화요일 운영 시작시간이 누락되었습니다.")
    private Integer tuesdayOperatingStartAt;
    @NotNull(message = "화요일 운영 마감시간이 누락되었습니다.")
    private Integer tuesdayOperatingEndAt;
    @NotNull(message = "화요일 점심시간 시작이 누락되었습니다.")
    private Integer tuesdayBreakStartAt;
    @NotNull(message = "화요일 점심시간 마감이 누락되었습니다.")
    private Integer tuesdayBreakEndAt;
    @NotNull(message = "화요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean tuesdayNightCareEnable;
    @NotNull(message = "화요일 야간진료 시작시간이 누락되었습니다.")
    private Integer tuesdayNightCareStartAt;
    @NotNull(message = "화요일 야간진료 마감시간이 누락되었습니다.")
    private Integer tuesdayNightCareEndAt;

    @NotNull(message = "수요일 운영여부가 누락되었습니다.")
    private Boolean wednesdayOperatingEnable;
    @NotNull(message = "수요일 운영 시작시간이 누락되었습니다.")
    private Integer wednesdayOperatingStartAt;
    @NotNull(message = "수요일 운영 마감시간이 누락되었습니다.")
    private Integer wednesdayOperatingEndAt;
    @NotNull(message = "수요일 점심시간 시작이 누락되었습니다.")
    private Integer wednesdayBreakStartAt;
    @NotNull(message = "수요일 점심시간 마감이 누락되었습니다.")
    private Integer wednesdayBreakEndAt;
    @NotNull(message = "수요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean wednesdayNightCareEnable;
    @NotNull(message = "수요일 야간진료 시작시간이 누락되었습니다.")
    private Integer wednesdayNightCareStartAt;
    @NotNull(message = "수요일 야간진료 마감시간이 누락되었습니다.")
    private Integer wednesdayNightCareEndAt;

    @NotNull(message = "목요일 운영여부가 누락되었습니다.")
    private Boolean thursdayOperatingEnable;
    @NotNull(message = "목요일 운영 시작시간이 누락되었습니다.")
    private Integer thursdayOperatingStartAt;
    @NotNull(message = "목요일 운영 마감시간이 누락되었습니다.")
    private Integer thursdayOperatingEndAt;
    @NotNull(message = "목요일 점심시간 시작이 누락되었습니다.")
    private Integer thursdayBreakStartAt;
    @NotNull(message = "목요일 점심시간 마감이 누락되었습니다.")
    private Integer thursdayBreakEndAt;
    @NotNull(message = "목요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean thursdayNightCareEnable;
    @NotNull(message = "목요일 야간진료 시작시간이 누락되었습니다.")
    private Integer thursdayNightCareStartAt;
    @NotNull(message = "목요일 야간진료 마감시간이 누락되었습니다.")
    private Integer thursdayNightCareEndAt;

    @NotNull(message = "금요일 운영여부가 누락되었습니다.")
    private Boolean fridayOperatingEnable;
    @NotNull(message = "금요일 운영 시작시간이 누락되었습니다.")
    private Integer fridayOperatingStartAt;
    @NotNull(message = "금요일 운영 마감시간이 누락되었습니다.")
    private Integer fridayOperatingEndAt;
    @NotNull(message = "금요일 점심시간 시작이 누락되었습니다.")
    private Integer fridayBreakStartAt;
    @NotNull(message = "금요일 점심시간 마감이 누락되었습니다.")
    private Integer fridayBreakEndAt;
    @NotNull(message = "금요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean fridayNightCareEnable;
    @NotNull(message = "금요일 야간진료 시작시간이 누락되었습니다.")
    private Integer fridayNightCareStartAt;
    @NotNull(message = "금요일 야간진료 마감시간이 누락되었습니다.")
    private Integer fridayNightCareEndAt;

    @NotNull(message = "토요일 운영여부가 누락되었습니다.")
    private Boolean saturdayOperatingEnable;
    @NotNull(message = "토요일 운영 시작시간이 누락되었습니다.")
    private Integer saturdayOperatingStartAt;
    @NotNull(message = "토요일 운영 마감시간이 누락되었습니다.")
    private Integer saturdayOperatingEndAt;
    @NotNull(message = "토요일 점심시간 시작이 누락되었습니다.")
    private Integer saturdayBreakStartAt;
    @NotNull(message = "토요일 점심시간 마감이 누락되었습니다.")
    private Integer saturdayBreakEndAt;
    @NotNull(message = "토요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean saturdayNightCareEnable;
    @NotNull(message = "토요일 야간진료 시작시간이 누락되었습니다.")
    private Integer saturdayNightCareStartAt;
    @NotNull(message = "토요일 야간진료 마감시간이 누락되었습니다.")
    private Integer saturdayNightCareEndAt;

    @NotNull(message = "일요일 운영여부가 누락되었습니다.")
    private Boolean sundayOperatingEnable;
    @NotNull(message = "일요일 운영 시작시간이 누락되었습니다.")
    private Integer sundayOperatingStartAt;
    @NotNull(message = "일요일 운영 마감시간이 누락되었습니다.")
    private Integer sundayOperatingEndAt;
    @NotNull(message = "일요일 점심시간 시작이 누락되었습니다.")
    private Integer sundayBreakStartAt;
    @NotNull(message = "일요일 점심시간 마감이 누락되었습니다.")
    private Integer sundayBreakEndAt;
    @NotNull(message = "일요일 야간진료 운영여부가 누락되었습니다.")
    private Boolean sundayNightCareEnable;
    @NotNull(message = "일요일 야간진료 시작시간이 누락되었습니다.")
    private Integer sundayNightCareStartAt;
    @NotNull(message = "일요일 야간진료 마감시간이 누락되었습니다.")
    private Integer sundayNightCareEndAt;
}
