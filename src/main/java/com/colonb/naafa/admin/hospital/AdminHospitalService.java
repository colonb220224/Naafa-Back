package com.colonb.naafa.admin.hospital;

import com.colonb.naafa.admin.hospital.dto.HospitalDto;
import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.hospital.HospitalMapper;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.enums.AccountStatus;
import com.colonb.naafa.user.enums.HospitalRole;
import com.colonb.naafa.user.enums.UserRole;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminHospitalService {
    private final HospitalMapper hospitalMapper;
    private final UserMapper userMapper;

    @Transactional
    public Result hospitalModify(HospitalDto req, long hospitalSeq, UserDetailsImpl userDetails) throws Exception {
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        // 병원 고유번호 삽입
        mappedReq.put("hospital", hospitalSeq);
        // 대표관리자 고유번호
        Optional<Integer> userSeq = hospitalMapper.findUserByHospital((Long) mappedReq.get("hospital"));
        if(!userSeq.isPresent()){
            return new Result("병원 대표관리자 정보가 존재하지 않습니다.",HttpStatus.BAD_REQUEST, false);
        }
        mappedReq.put("user", userSeq);
        userMapper.updateDefaultUser(mappedReq);
        // 병원 테이블 수정
        hospitalMapper.updateHospital(mappedReq);
        // 병원장 테이블 수정 HOSPITAL_CHIEF 테이블에 HOSPITAL 외래키가 필요함
        hospitalMapper.updateHospitalChief(mappedReq);
        // 병원 대표관리자 수정
        hospitalMapper.updateUserHospitalHost(mappedReq);
        // 병원 INFO 수정
        hospitalMapper.updateHospitalInfo(mappedReq);
        // 에약 서비스 제공 항목 - 방문 예약 수정
        hospitalMapper.updateHospitalVisitReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 비대면 상담 수정
        hospitalMapper.updateHospitalConsultationReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 온라인 접수 상담 수정
        hospitalMapper.updateHospitalOnlineReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 왕진 예약 상담 수정
        hospitalMapper.updateHospitalHouseCallReserveInfo(mappedReq);
        // 병원 운영시간 수정 - 월요일
        hospitalMapper.updateHospitalOperatingHourMonday(mappedReq);
        // 병원 운영시간 수정 - 화요일
        hospitalMapper.updateHospitalOperatingHourTuesday(mappedReq);
        // 병원 운영시간 수정 - 수요일
        hospitalMapper.updateHospitalOperatingHourWednesday(mappedReq);
        // 병원 운영시간 수정 - 목요일
        hospitalMapper.updateHospitalOperatingHourThursday(mappedReq);
        // 병원 운영시간 수정 - 금요일
        hospitalMapper.updateHospitalOperatingHourFriday(mappedReq);
        // 병원 운영시간 수정 - 토요일
        hospitalMapper.updateHospitalOperatingHourSaturday(mappedReq);
        // 병원 운영시간 수정 - 일요일
        hospitalMapper.updateHospitalOperatingHourSunday(mappedReq);

        return new Result(HttpStatus.OK, true);
    }


    @Transactional
    public Result hospitalAdd(HospitalDto req, UserDetailsImpl userDetails) throws Exception {
        if(userMapper.existByUsername(req.getUsername())){
            return new Result("이미 존재하는 이메일입니다.",HttpStatus.BAD_REQUEST, false);
        }
        // 대표관리자 유저정보 등록
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        mappedReq.put("role", UserRole.ROLE_USER);
        userMapper.insertDefaultUser(mappedReq);
        // todo 소속팀/직급/회원분류(운영관리 기획서 6p)에 대한 테이블 및 기획이 필요
        userMapper.insertUserCreatedAt(mappedReq);
        mappedReq.put("userRole", HospitalRole.HOSPITAL_MASTER);
        userMapper.insertUserRoleDetails(mappedReq);
        mappedReq.put("status", AccountStatus.NORMAL);
        userMapper.insertUserStatus(mappedReq);
        // 병원 테이블 등록
        hospitalMapper.insertHospital(mappedReq);
        // 병원장 테이블 등록
        hospitalMapper.insertHospitalChief(mappedReq);
        // 병원 대표관리자 등록 USER_HOSPITAL_RELATE
        hospitalMapper.insertUserHospitalHost(mappedReq);
        // 병원 INFO 등록
        hospitalMapper.insertHospitalInfo(mappedReq);
        // 에약 서비스 제공 항목 - 방문 예약 등록
        hospitalMapper.insertHospitalVisitReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 비대면 상담 등록
        hospitalMapper.insertHospitalConsultationReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 온라인 접수 상담 등록
        hospitalMapper.insertHospitalOnlineReserveInfo(mappedReq);
        // 에약 서비스 제공 항목 - 왕진 예약 상담 등록
        hospitalMapper.insertHospitalHouseCallReserveInfo(mappedReq);
        // 병원 운영시간 등록 - 월요일
        hospitalMapper.insertHospitalOperatingHourMonday(mappedReq);
        // 병원 운영시간 등록 - 화요일
        hospitalMapper.insertHospitalOperatingHourTuesday(mappedReq);
        // 병원 운영시간 등록 - 수요일
        hospitalMapper.insertHospitalOperatingHourWednesday(mappedReq);
        // 병원 운영시간 등록 - 목요일
        hospitalMapper.insertHospitalOperatingHourThursday(mappedReq);
        // 병원 운영시간 등록 - 금요일
        hospitalMapper.insertHospitalOperatingHourFriday(mappedReq);
        // 병원 운영시간 등록 - 토요일
        hospitalMapper.insertHospitalOperatingHourSaturday(mappedReq);
        // 병원 운영시간 등록 - 일요일
        hospitalMapper.insertHospitalOperatingHourSunday(mappedReq);


        return new Result(HttpStatus.OK, true);
    }

}
