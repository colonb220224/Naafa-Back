package com.colonb.naafa.inquiry;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Optional;

@Mapper
public interface InquiryMapper {

    int insertInquiry(HashMap<String, Object> param);

    int deleteInquiry(long seq);

    Optional<HashMap<String, Object>> findBySeq(long seq);

    int updateInquiry(HashMap<String, Object> param);
}
