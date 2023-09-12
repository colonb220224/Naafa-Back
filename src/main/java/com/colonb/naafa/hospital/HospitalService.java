package com.colonb.naafa.hospital;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.hospital.dto.HospitalDefaultDto;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.enums.AccountStatus;
import com.colonb.naafa.user.enums.HospitalRole;
import com.colonb.naafa.user.enums.PatientRelate;
import com.colonb.naafa.user.enums.UserRole;
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
        // 대표 관리자 유저정보 등록
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        userMapper.insertDefaultUser(mappedReq);
        // todo 대표 관리자 유저 마케팅 동의에 대한 기획이 없음
//        userMapper.insertUserMarketing(mappedReq);
        userMapper.insertUserCreatedAt(mappedReq);
        // todo 유저 롤에 대한 테이블 로직 분리 필요함 // 유저매퍼에서 사용하는 role에 대한 로직 분리
        mappedReq.put("role", HospitalRole.HOSPITAL_MASTER);
        userMapper.insertUserRole(mappedReq);
        mappedReq.put("status", AccountStatus.NORMAL);
        userMapper.insertUserStatus(mappedReq);
        // 병원 테이블 등록
        hospitalMapper.insertHospital(mappedReq);
        // 병원장 테이블 등록
        hospitalMapper.insertHospitalChief(mappedReq);
        System.out.println(mappedReq.toString());
        // 병원 대표관리자 등록
        hospitalMapper.insertUserHospitalHost(mappedReq);

        return new Result(HttpStatus.OK, true);
    }

}
