package com.colonb.naafa.inquiry;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.inquiry.dto.InquiryAnswerDto;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryMapper inquiryMapper;

    private final UserMapper userMapper;


    @Transactional
    public Result add(InquiryDto req, UserDetailsImpl userDetails) {
        if(!(userMapper.findHospitalRoleByUser(userDetails.getUser().getSeq()) == HospitalRole.PATIENT)){
            return new Result("병원 관계자는 문의 사항을 남길 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("user", userDetails.getUser().getSeq());
        inquiryMapper.insertInquiry(param);
        return new Result(HttpStatus.OK, true);
    }

    @Transactional
    public Result modify(long seq, InquiryDto req, UserDetailsImpl userDetails) {
        Optional<HashMap<String, Object>> data = inquiryMapper.findBySeq(seq);
        if(!data.isPresent()){
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if(Long.parseLong(data.get().get("USER").toString()) != userDetails.getUser().getSeq()){
            return new Result("본인의 문의 사항만 수정 가능합니다.", HttpStatus.BAD_REQUEST, false);
        }
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("seq", seq);
        inquiryMapper.updateInquiry(param);
        return new Result(HttpStatus.OK, true);
    }


    @Transactional
    public Result remove(long seq, UserDetailsImpl userDetails) {
        Optional<HashMap<String, Object>> data = inquiryMapper.findBySeq(seq);
        if(!data.isPresent()){
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if(Long.parseLong(data.get().get("USER").toString()) != userDetails.getUser().getSeq()){
            return new Result("본인의 문의 사항만 삭제 가능합니다.", HttpStatus.BAD_REQUEST, false);
        }
        inquiryMapper.deleteInquiry(seq);
        return new Result(HttpStatus.OK, true);
    }

    public Result list(UserDetailsImpl userDetails) {
        List<HashMap<String, Object>> result  = inquiryMapper.findByUser(userDetails.getUser().getSeq());
        return new Result(HttpStatus.OK, result, true);
    }

    @Transactional
    public Result answerAdd(InquiryAnswerDto req, long seq, UserDetailsImpl userDetails) {
        if(userMapper.findHospitalRoleByUser(userDetails.getUser().getSeq()) == HospitalRole.PATIENT){
            return new Result("병원 관계자만 문의 사항을 남길 수 있습니다.", HttpStatus.BAD_REQUEST, false);
        }
        Optional<HashMap<String, Object>> data = inquiryMapper.findBySeq(seq);
        if(!data.isPresent()){
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("inquiry", seq);
        param.put("user", userDetails.getUser().getSeq());
        inquiryMapper.insertInquiryAnswer(param);
        return new Result(HttpStatus.OK, true);
    }
}
