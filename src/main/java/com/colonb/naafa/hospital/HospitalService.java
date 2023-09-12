package com.colonb.naafa.hospital;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.hospital.dto.HospitalDefaultDto;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.enums.*;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalMapper hospitalMapper;
    private final UserMapper userMapper;

    @Transactional
    public Result hospitalAdd(HospitalDefaultDto req, UserDetailsImpl userDetails) throws Exception {
        if(userMapper.existByUsername(req.getUsername())){
            return new Result("이미 존재하는 이메일입니다.",HttpStatus.BAD_REQUEST, false);
        }
        // 대표관리자 유저정보 등록
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        userMapper.insertDefaultUser(mappedReq);
        // todo 대표관리자 유저 마케팅 동의에 대한 기획이 없음
//        userMapper.insertUserMarketing(mappedReq);
        userMapper.insertUserCreatedAt(mappedReq);
        mappedReq.put("role", HospitalRole.HOSPITAL_MASTER);
        userMapper.insertUserRole(mappedReq);
        mappedReq.put("status", AccountStatus.NORMAL);
        userMapper.insertUserStatus(mappedReq);
        mappedReq.put("adminRole", AdminRole.MANAGER);
        userMapper.insertAdminRole(mappedReq);
        // 병원 테이블 등록
        hospitalMapper.insertHospital(mappedReq);
        // 병원장 테이블 등록
        hospitalMapper.insertHospitalChief(mappedReq);
        // 병원 대표관리자 등록
        hospitalMapper.insertUserHospitalHost(mappedReq);

        return new Result(HttpStatus.OK, true);
    }

}
