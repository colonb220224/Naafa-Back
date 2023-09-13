package com.colonb.naafa.admin.hospital;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface HospitalMapper {
    int insertUserHospitalHost(HashMap<String, Object> param);
    int insertHospitalChief(HashMap<String, Object> param);
    int insertHospital(HashMap<String, Object> param);
}
