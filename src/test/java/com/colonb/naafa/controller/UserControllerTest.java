package com.colonb.naafa.controller;

import com.colonb.naafa.auth.oauth2.jwt.JwtTokenProvider;
import com.colonb.naafa.user.UserMapper;
import com.colonb.naafa.user.entity.User;
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

    @BeforeEach
    void setup(){
        HashMap<String, Object> req = new HashMap<>();
        req.put("username", "setup@username.com");
        req.put("password", passwordEncoder.encode("test"));
        req.put("phone", "01012345678");
        userMapper.insertDefaultUser(req);
        User user = userMapper.findByUsername("setup@username.com").get();
        setupToken = jwtTokenProvider.createToken(user);
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
}
