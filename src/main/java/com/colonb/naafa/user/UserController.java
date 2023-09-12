package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/patient/list")
    public ResponseEntity<Result> patientList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientList(userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @GetMapping("/patient/view/{seq}")
    public ResponseEntity<Result> patientView(@PathVariable long seq,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientView(seq, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("/patient/add")
    public ResponseEntity<Result> patientAdd(@RequestBody PatientDto req,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        Result res = userService.patientAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("/patient/modify/{seq}")
    public ResponseEntity<Result> patientModify(@PathVariable long seq,
                                                @RequestBody PatientDto req,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientModify(seq, req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @DeleteMapping("/patient/remove/{seq}")
    public ResponseEntity<Result> patientRemove(@PathVariable long seq,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientRemove(seq, userDetails);
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
