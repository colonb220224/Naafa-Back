package com.colonb.naafa.hospital;

import com.colonb.naafa.auth.UserDetailsImpl;
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
import org.thymeleaf.util.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalMapper hospitalMapper;
    private final UserMapper userMapper;

    @Transactional
    public Result getHospitalDetail(long seq) {
        // 병원 상세보기
        HashMap<String, Object> result  = hospitalMapper.findHospitalByHospital(seq);
        System.out.println(result);
        return new Result(HttpStatus.OK, result, true);
    }

    @Transactional
    public Result getHospitalList() {
        // 병원리스트 추가
        List<HashMap<String, Object>> result  = hospitalMapper.findHospitalList();

        return new Result(HttpStatus.OK, result, true);
    }

}
