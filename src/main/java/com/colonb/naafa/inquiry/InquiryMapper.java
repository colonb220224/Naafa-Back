package com.colonb.naafa.inquiry;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface InquiryMapper {

    int insertInquiry(HashMap<String, Object> param);

    int deleteInquiry(long seq);

    Optional<HashMap<String, Object>> findBySeq(long seq);

    int updateInquiry(HashMap<String, Object> param);

    List<HashMap<String, Object>> findByUser(long seq);

    int insertInquiryAnswer(HashMap<String, Object> param);

    Optional<HashMap<String, Object>> findWithAnswerBySeq(long seq);
}
