package com.colonb.naafa.hospital;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Mapper
public interface HospitalMapper {
    int updateHospitalOperatingHourMonday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourTuesday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourWednesday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourThursday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourFriday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourSaturday(HashMap<String, Object> mappedReq);
    int updateHospitalOperatingHourSunday(HashMap<String, Object> mappedReq);

    int insertHospitalOperatingHourMonday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourTuesday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourWednesday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourThursday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourFriday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourSaturday(HashMap<String, Object> mappedReq);
    int insertHospitalOperatingHourSunday(HashMap<String, Object> mappedReq);
    HashMap<String, Object> findHospitalByHospital(long seq);
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
    int insertHospitalVisitReserveInfo(HashMap<String, Object> mappedReq);
    int insertHospitalConsultationReserveInfo(HashMap<String, Object> mappedReq);
    int insertHospitalOnlineReserveInfo(HashMap<String, Object> mappedReq);
    int insertHospitalHouseCallReserveInfo(HashMap<String, Object> mappedReq);

    int updateHospitalVisitReserveInfo(HashMap<String, Object> mappedReq);
    int updateHospitalConsultationReserveInfo(HashMap<String, Object> mappedReq);
    int updateHospitalOnlineReserveInfo(HashMap<String, Object> mappedReq);
    int updateHospitalHouseCallReserveInfo(HashMap<String, Object> mappedReq);
}
