package com.colonb.naafa.user;

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

    @PostMapping("login")
    public ResponseEntity<HashMap<String, Object>> login(@RequestBody LoginDto req) {

        return new ResponseEntity<>(userService.login(req), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<HashMap<String, Object>> register(@RequestBody RegisterDto req) {

        return new ResponseEntity<>(userService.register(req), HttpStatus.OK);
    }
}
