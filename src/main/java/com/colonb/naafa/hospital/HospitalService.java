package com.colonb.naafa.hospital;

import com.colonb.naafa.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalMapper hospitalMapper;
    private final UserMapper userMapper;

    @Transactional
    public HashMap<String, Object> getHospitalDetail(long seq) {
        // 병원 상세보기
        HashMap<String, Object> result = hospitalMapper.findHospitalByHospital(seq);

        return result;
    }

    @Transactional
    public List<HashMap<String, Object>> getHospitalList() {
        // 병원리스트 추가
        List<HashMap<String, Object>> result = hospitalMapper.findHospitalList();

        return result;
    }

}
