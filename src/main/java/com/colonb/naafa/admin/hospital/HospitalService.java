package com.colonb.naafa.admin.hospital;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.admin.hospital.dto.HospitalDto;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.user.enums.*;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalMapper hospitalMapper;
    private final UserMapper userMapper;

    @Transactional
    public Result hospitalModify(HospitalDto req, long hospitalSeq, long userSeq, UserDetailsImpl userDetails) throws Exception {
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        // 병원 고유번호 삽입
        mappedReq.put("hospital", hospitalSeq);
        mappedReq.put("user", userSeq);
        // 대표관리자가 동일인이라면 update
        userMapper.updateDefaultUser(mappedReq);
        // 병원 테이블 수정
        hospitalMapper.updateHospital(mappedReq);
//        // 병원장 테이블 수정 HOSPITAL_CHIEF 테이블에 HOSPITAL 외래키가 필요함
//        hospitalMapper.updateHospitalChief(mappedReq);
        // 병원 대표관리자 수정
        hospitalMapper.updateUserHospitalHost(mappedReq);
        // 병원 INFO 수정
        hospitalMapper.updateHospitalInfo(mappedReq);

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
        // 병원 대표관리자 등록
        hospitalMapper.insertUserHospitalHost(mappedReq);
        // 병원 INFO 등록
        hospitalMapper.insertHospitalInfo(mappedReq);

        return new Result(HttpStatus.OK, true);
    }

}
