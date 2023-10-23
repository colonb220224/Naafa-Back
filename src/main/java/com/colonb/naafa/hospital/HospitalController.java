package com.colonb.naafa.hospital;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;


//    @GetMapping("{seq}")
//    public ResponseEntity<Result> getHospitalDetail(@PathVariable long seq) {
//        Result res = hospitalService.getHospitalDetail(seq);
//        return ResponseEntity.status(res.status()).body(res);
//    }

//    @GetMapping("list")
//    public ResponseEntity<Result> getHospitalList() {
//        Result res = hospitalService.getHospitalList();
//        return ResponseEntity.status(res.status()).body(res);
//    }
}
