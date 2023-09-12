package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.user.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.RegisterDto;
import com.colonb.naafa.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@RestController("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/patient/add")
    public ResponseEntity<String> patientAdd(@RequestBody PatientDto req,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return new ResponseEntity<>(userService.patientAdd(req, userDetails), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<HashMap<String, Object>> login (@RequestBody LoginDto req){

        return new ResponseEntity<>(userService.login(req), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> register (@RequestBody RegisterDto req){

        return new ResponseEntity<>(userService.register(req), HttpStatus.OK);
    }
}
