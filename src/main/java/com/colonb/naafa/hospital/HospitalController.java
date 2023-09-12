package com.colonb.naafa.hospital;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.hospital.dto.HospitalDefaultDto;
import com.colonb.naafa.result.Result;
import com.colonb.naafa.user.UserService;
import com.colonb.naafa.user.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @PostMapping("add")
    public ResponseEntity<Result> hospitalAdd(@RequestBody HospitalDefaultDto req,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        Result res = hospitalService.hospitalAdd(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
