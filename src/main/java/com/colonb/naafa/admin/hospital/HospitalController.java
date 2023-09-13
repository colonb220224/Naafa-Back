package com.colonb.naafa.admin.hospital;

import com.colonb.naafa.admin.hospital.dto.HospitalDefaultDto;
import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.result.Result;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("add")
    public ResponseEntity<Result> hospitalAdd(@RequestBody @Valid HospitalDefaultDto req,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = hospitalService.hospitalAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
