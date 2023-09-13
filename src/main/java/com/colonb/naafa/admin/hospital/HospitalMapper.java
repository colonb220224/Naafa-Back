package com.colonb.naafa.admin.hospital;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface HospitalMapper {
    int updateHospitalInfo(HashMap<String, Object> param);
    int updateUserHospitalHost(HashMap<String, Object> param);
//    int updateHospitalChief(HashMap<String, Object> param);
    int updateHospital(HashMap<String, Object> param);
    int insertHospitalInfo(HashMap<String, Object> param);
    int insertUserHospitalHost(HashMap<String, Object> param);
    int insertHospitalChief(HashMap<String, Object> param);
    int insertHospital(HashMap<String, Object> param);
}
