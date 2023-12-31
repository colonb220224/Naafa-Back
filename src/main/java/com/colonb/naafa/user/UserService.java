package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.dto.RegisterDto;
import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.user.enums.AccountStatus;
import com.colonb.naafa.user.enums.HospitalRole;
import com.colonb.naafa.user.enums.PatientRelate;
import com.colonb.naafa.user.enums.SocialProvider;
import com.colonb.naafa.util.AES256Encrypt;
import com.colonb.naafa.util.HashMapConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Result patientList(UserDetailsImpl userDetails) throws Exception{
        List<HashMap<String, Object>> result = userMapper.findPatientByUser(userDetails.getUser().getSeq());
        for(HashMap<String, Object> data : result){
            data.put("SOCIAL_NUMBER",  AES256Encrypt.decrypt(data.get("SOCIAL_NUMBER").toString()));
        }
        return new Result(HttpStatus.OK, result, true);
    }

    public Result patientView(UserDetailsImpl userDetails, long seq) throws Exception {
        Optional<HashMap<String, Object>> result  = userMapper.findPatientBySeq(seq);
        if (!result.isPresent()) {
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if(Long.parseLong(result.get().get("USER").toString()) != userDetails.getUser().getSeq()){
            return new Result("본인의 구성원만 상세보기 확인이 가능합니다.", HttpStatus.BAD_REQUEST, false);
        }
        result.get().put("SOCIAL_NUMBER",  AES256Encrypt.decrypt(result.get().get("SOCIAL_NUMBER").toString()));
        return new Result(HttpStatus.OK, result, true);
    }

    @Transactional
    public Result patientAdd(PatientDto req, UserDetailsImpl userDetails) throws Exception {
        if(!(userMapper.findHospitalRoleByUser(userDetails.getUser().getSeq()) == HospitalRole.PATIENT)){
            return new Result("병원 관계자는 구성원을 추가할 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (req.getRelate() != PatientRelate.SELF) {
            if (!userMapper.existSelfPatientByUser(userDetails.getUser().getSeq())) {
                return new Result("본인 세부 정보를 먼저 작성해야 합니다.", HttpStatus.BAD_REQUEST, false);
            }
        }
        if (req.getRelate() == PatientRelate.SELF) {
            if (userMapper.existSelfPatientByUser(userDetails.getUser().getSeq())) {
                return new Result("본인은 이미 등록되어 있습니다.", HttpStatus.BAD_REQUEST, false);
            }
        }
        req.encryptSocialNumber();
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("user", userDetails.getUser().getSeq());
        userMapper.insertPatient(param);
        return new Result(HttpStatus.OK, true);
    }

    @Transactional
    public Result patientModify(long seq, PatientDto req, UserDetailsImpl userDetails) throws Exception {
        Optional<HashMap<String, Object>> data = userMapper.findPatientBySeq(seq);
        if (!data.isPresent()) {
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (Long.parseLong(data.get().get("USER").toString()) != userDetails.getUser().getSeq()) {
            return new Result("본인의 구성원만 수정 가능합니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (data.get().get("RELATE").toString().equals("SELF") && !(req.getRelate() == PatientRelate.SELF)) {
            return new Result("본인의 관계는 수정할 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (req.getRelate() != PatientRelate.SELF && data.get().get("RELATE").toString().equals("SELF")) {
            return new Result("구성원을 본인으로 변경 할 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        req.encryptSocialNumber();
        HashMap<String, Object> param = HashMapConverter.convert(req);
        param.put("seq", seq);
        userMapper.updatePatient(param);
        return new Result(HttpStatus.OK, true);
    }

    @Transactional
    public Result patientRemove(long seq, UserDetailsImpl userDetails) {
        Optional<HashMap<String, Object>> data = userMapper.findPatientBySeq(seq);
        if (!data.isPresent()) {
            return new Result("존재 하지 않는 seq 입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (Long.parseLong(data.get().get("USER").toString()) != userDetails.getUser().getSeq()) {
            return new Result("본인의 구성원만 삭제 가능합니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (data.get().get("RELATE").toString().equals("SELF")) {
            return new Result("본인은 삭제하실 수 없습니다.", HttpStatus.BAD_REQUEST, false);
        }
        userMapper.deletePatient(seq);
        return new Result(HttpStatus.OK, true);
    }

    @Transactional
    public Result login(LoginDto req) {
        Optional<User> optionalUser = userMapper.findByUsername(req.getUsername());
        if (!optionalUser.isPresent()) {
            return new Result("잘못된 이메일 혹은 패스워드입니다.", HttpStatus.BAD_REQUEST, false);
        }
        User user = optionalUser.get();
        if (user.getProvider() != SocialProvider.DEFAULT) {
            return new Result("소셜로그인을 사용해주세요.", HttpStatus.BAD_REQUEST, false);
        }
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return new Result("잘못된 이메일 혹은 패스워드입니다.", HttpStatus.BAD_REQUEST, false);
        }
        AccountStatus status = userMapper.findAccountStatusByUser(user.getSeq());
        if (status == AccountStatus.DORMANT) {
            return new Result("휴면 회원입니다.", HttpStatus.BAD_REQUEST, false);
        }
        if (status == AccountStatus.WITHDRAWAL) {
            return new Result("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST, false);
        }
        return new Result(HttpStatus.OK, jwtTokenProvider.createToken(user), true);
    }

    @Transactional
    public Result register(RegisterDto req) {
        if (userMapper.existByUsername(req.getUsername())) {
            return new Result("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST, false);
        }
        HashMap<String, Object> mappedReq = HashMapConverter.convert(req);
        mappedReq.replace("password", passwordEncoder.encode(mappedReq.get("password").toString()));
        userMapper.insertDefaultUser(mappedReq);
        userMapper.insertUserMarketing(mappedReq);
        userMapper.insertUserCreatedAt(mappedReq);
        mappedReq.put("userRole", HospitalRole.PATIENT);
        userMapper.insertUserRoleDetails(mappedReq);
        mappedReq.put("status", AccountStatus.NORMAL);
        userMapper.insertUserStatus(mappedReq);
        return new Result(HttpStatus.OK, true);
    }

}
