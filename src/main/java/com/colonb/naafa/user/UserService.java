package com.colonb.naafa.user;

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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final HashMapConverter hashMapConverter;
    private final JwtTokenProvider jwtTokenProvider;

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
