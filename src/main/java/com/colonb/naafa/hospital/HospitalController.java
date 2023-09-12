package com.colonb.naafa.hospital;

import com.colonb.naafa.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

}
