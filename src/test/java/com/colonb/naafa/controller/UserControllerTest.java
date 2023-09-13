package com.colonb.naafa.controller;

import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
import com.colonb.naafa.util.AES256Encrypt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String setupToken;
    private User setupUser;

    @BeforeEach
    void setup() {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "setup@username.com");
        req.put("password", passwordEncoder.encode("test"));
        req.put("phone", "01012345678");
        userMapper.insertDefaultUser(req);
        setupUser = userMapper.findByUsername("setup@username.com").get();
        setupToken = jwtTokenProvider.createToken(setupUser);
    }


    @Test
    @Transactional
    @DisplayName("회원가입 - 성공")
    void registerSuccess() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "test@username.com");
        req.put("password", "testpassword");
        req.put("phone", "01012345678");
        req.put("marketing", false);
        ResultActions ra = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("회원가입 - 실패_중복아이디")
    void registerFailDubUsername() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "setup@username.com");
        req.put("password", "testpassword");
        req.put("phone", "01012345678");
        req.put("marketing", false);
        ResultActions ra = mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("이미 존재하는 이메일입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }


    @Test
    @Transactional
    @DisplayName("로그인 - 성공")
    void loginSuccess() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "setup@username.com");
        req.put("password", "test");
        ResultActions ra = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("data").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 실패_잘못된 암호")
    void loginFailWrongPwd() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "setup@username.com");
        req.put("password", "wrong");
        ResultActions ra = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("잘못된 이메일 혹은 패스워드입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("로그인 - 실패_잘못된 아이디")
    void loginFailWrongUsername() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "wrong@username.com");
        req.put("password", "test");
        ResultActions ra = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("잘못된 이메일 혹은 패스워드입니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("환자본인추가 - 성공")
    void addPatientSelfSuccess() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("name", "테스트이름");
        req.put("relate", "SELF");
        req.put("socialNumber","123456-1234567");
        ResultActions ra = mockMvc.perform(post("/user/auth/patient/add")
                .header("Authorization", setupToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());

        ra
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("환자본인추가 - 실패_이미존재")
    void addPatientSelfFailAlreadyExist() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("name", "테스트이름");
        req.put("relate", "SELF");
        req.put("socialNumber", AES256Encrypt.encrypt("123456-1234567"));
        req.put("user",setupUser.getSeq());
        userMapper.insertPatient(req);
        ResultActions ra = mockMvc.perform(post("/user/auth/patient/add")
                .header("Authorization", setupToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());
        ra
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("본인은 이미 등록되어 있습니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("환자추가 - 성공")
    void addPatientSuccess() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("name", "테스트이름");
        req.put("relate", "SELF");
        req.put("socialNumber","123456-1234567");
        req.put("user",setupUser.getSeq());
        userMapper.insertPatient(req);
        req.replace("relate","SPOUSE");
        ResultActions ra = mockMvc.perform(post("/user/auth/patient/add")
                .header("Authorization", setupToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());
        ra
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("환자추가 - 실패_본인등록필요")
    void addPatientFailAddSelfFirst() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("name", "테스트이름");
        req.put("relate", "SPOUSE");
        req.put("socialNumber","123456-1234567");
        req.put("user",setupUser.getSeq());
        userMapper.insertPatient(req);
        ResultActions ra = mockMvc.perform(post("/user/auth/patient/add")
                .header("Authorization", setupToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
                .accept(MediaType.APPLICATION_JSON)).andDo(print());
        ra
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("본인 세부 정보를 먼저 작성해야 합니다."))
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @Transactional
    @DisplayName("구성원 조회 - 성공")
    void viewPatientsSuccess() throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("name", "테스트이름");
        req.put("relate", "SELF");
        req.put("socialNumber","123456-1234567");
        req.put("user",setupUser.getSeq());
        userMapper.insertPatient(req);
        ResultActions ra = mockMvc.perform(get("/user/auth/patient/list")
                .header("Authorization", setupToken)
                .accept(MediaType.APPLICATION_JSON)).andDo(print());
        ra
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true))
                .andExpect(jsonPath("message").value("요청에 성공했습니다."))
                .andExpect(jsonPath("data").isArray());
    }

    @Test
    @Transactional
    @DisplayName("구성원 조회 - 실패_미로그인")
    void viewPatientsFailNoAuth() throws Exception {
        ResultActions ra = mockMvc.perform(get("/user/auth/patient/list")
                .accept(MediaType.APPLICATION_JSON)).andDo(print());
        ra
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("success").value(false))
                .andExpect(jsonPath("message").value("로그인이 필요합니다."))
                .andExpect(jsonPath("data").isEmpty());
    }
}
