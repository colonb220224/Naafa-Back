package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.dto.LoginDto;
import com.colonb.naafa.user.dto.PatientDto;
import com.colonb.naafa.user.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @GetMapping("auth/patient/list")
    public ResponseEntity<Result> patientList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = userService.patientList(userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }


    @PostMapping("auth/patient/add")
    public ResponseEntity<Result> patientAdd(@RequestBody @Valid PatientDto req,
                                             BindingResult bindingResul,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST , false), HttpStatus.BAD_REQUEST);
        }
        Result res = userService.patientAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("auth/patient/modify/{seq}")
    public ResponseEntity<Result> patientModify(@RequestBody @Valid PatientDto req,
                                                BindingResult bindingResul,
                                                @PathVariable long seq,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST , false), HttpStatus.BAD_REQUEST);
        }
        Result res = userService.patientModify(seq, req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @DeleteMapping("auth/patient/remove/{seq}")
    public ResponseEntity<Result> patientRemove(@PathVariable long seq,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("111");
        Result res = userService.patientRemove(seq, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("login")
    public ResponseEntity<Result> login(@RequestBody @Valid LoginDto req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = userService.login(req);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("register")
    public ResponseEntity<Result> register(@RequestBody @Valid RegisterDto req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = userService.register(req);
        return ResponseEntity.status(res.status()).body(res);
    }
}
