package com.colonb.naafa.hospital;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface HospitalMapper {
    List<HashMap<String, Object>> findHospitalList();
    Optional<Integer> findUserByHospital(long hospital);
    int updateHospitalInfo(HashMap<String, Object> param);
    int updateUserHospitalHost(HashMap<String, Object> param);
    int updateHospitalChief(HashMap<String, Object> param);
    int updateHospital(HashMap<String, Object> param);
    int insertHospitalInfo(HashMap<String, Object> param);
    int insertUserHospitalHost(HashMap<String, Object> param);
    int insertHospitalChief(HashMap<String, Object> param);
    int insertHospital(HashMap<String, Object> param);
}
