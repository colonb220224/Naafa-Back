package com.colonb.naafa.admin.hospital;

import com.colonb.naafa.admin.hospital.dto.HospitalDto;
import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("modify/{seq}")
    public ResponseEntity<Result> hospitalModify(@RequestBody @Valid HospitalDto req,
            BindingResult bindingResult, @PathVariable long seq, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = hospitalService.hospitalModify(req, seq,userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("add")
    public ResponseEntity<Result> hospitalAdd(@RequestBody @Valid HospitalDto req,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = hospitalService.hospitalAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
