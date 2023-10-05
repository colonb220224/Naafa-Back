package com.colonb.naafa.hospital;

import com.colonb.naafa.hospital.HospitalService;
import com.colonb.naafa.admin.hospital.dto.HospitalDto;
import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.hospital.dto.HospitalSeqDto;
import com.colonb.naafa.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("user/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping("{seq}")
    public ResponseEntity<Result> getHospitalDetail(@PathVariable long seq) {
        System.out.println(seq);
        Result res = hospitalService.getHospitalDetail(seq);
        return ResponseEntity.status(res.status()).body(res);
    }

    @GetMapping("list")
    public ResponseEntity<Result> getHospitalList() {
        Result res = hospitalService.getHospitalList();
        return ResponseEntity.status(res.status()).body(res);
    }
}
