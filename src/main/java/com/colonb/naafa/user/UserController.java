package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/patient/add")
    public ResponseEntity<Result> patientAdd(@RequestBody PatientDto req,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("login")
    public ResponseEntity<Result> login(@RequestBody LoginDto req) {
        Result res = userService.login(req);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("register")
    public ResponseEntity<Result> register(@RequestBody RegisterDto req) {
        Result res = userService.register(req);
        return ResponseEntity.status(res.status()).body(res);
    }
}
