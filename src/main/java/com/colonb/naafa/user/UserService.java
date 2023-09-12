package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.enums.PatientRelate;
import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.RegisterDto;
import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.user.enums.HospitalRole;
import com.colonb.naafa.user.enums.UserRole;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.swing.text.StyleContext;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
  
    private final HashMapConverter hashMapConverter;
  
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String patientAdd(PatientDto req, UserDetailsImpl userDetails) {

        // 본인 인증 안 되어있는 경우 구성원 추가 불가
        if(!req.getRelate().equals(PatientRelate.SELF)){
            Optional<HashMap<String, Object>> data = userMapper.findPatientByUser(userDetails.getUser().getSeq());
            if(!data.isPresent()){
                return "본인인증을 먼저 진행 후 구성원을 추가해 주세요.";
            }
        }

        HashMap<String, Object> param = hashMapConverter.convert(req);
        param.put("user", userDetails.getUser().getSeq());
        // TODO socialNumber 암호화 예정
        if(userMapper.insertPatient(param) != 1){
            throw new RuntimeException("구성원 추가에 실패하였습니다.");
        }
        return "구성원 추가에 성공하였습니다.";
    }

    @Transactional
    public HashMap<String, Object> login(LoginDto req) {
        Optional<User> user = userMapper.findByUsername(req.getUsername());
        if (!user.isPresent()) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "존재하지 않는 이메일입니다");
            return res;
        }

        if(!user.get().getPassword().equals(req.getPassword())){
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "비밀번호를 잘못 입력하셨습니다.");
            return res;
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("jwt", jwtTokenProvider.createToken(user.get()));
        return data;
    }

    @Transactional
    public HashMap<String, Object> register(RegisterDto req) {

        if (userMapper.findByUsername(req.getUsername()).isPresent()) {
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "해당 유저는 이미 가입된 유저입니다.");
            return res;
        }

        HashMap<String, Object> mappedReq = hashMapConverter.convert(req);

        if (userMapper.insertDefaultUser(mappedReq) != 1){
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "회원가입 중 오류가 발생하였습니다.");
            return res;
        }

        if (userMapper.insertUserMarketing(mappedReq) != 1){
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "회원가입 중 오류가 발생하였습니다.");
            return res;
        }

        if (userMapper.insertUserCreatedAt(mappedReq) != 1){
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "회원가입 중 오류가 발생하였습니다.");
            return res;
        }

        mappedReq.put("role", HospitalRole.PATIENT);
        if (userMapper.insertUserRole(mappedReq) != 1){
            HashMap<String, Object> res = new HashMap<>();
            res.put("msg", "회원가입 중 오류가 발생하였습니다.");
            return res;
        }

        return mappedReq;
    }

}
