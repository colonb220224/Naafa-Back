package com.colonb.naafa.inquiry;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.inquiry.dto.InquiryDto;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.enums.HospitalRole;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    private final UserMapper userMapper;


    @Transactional
    public Result add(InquiryDto req, UserDetailsImpl userDetails) {
        if(!(userMapper.findHospitalRoleByUser(userDetails.getUser().getSeq()) == HospitalRole.PATIENT)){
            return new Result("병원 관계자는 문의사항을 남길 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("user", userDetails.getUser().getSeq());
        inquiryMapper.insertInquiry(param);
        return new Result(HttpStatus.OK, true);
    }
}
