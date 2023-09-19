package com.colonb.naafa.inquiry;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface InquiryMapper {

    int insertInquiry(HashMap<String, Object> param);
}
