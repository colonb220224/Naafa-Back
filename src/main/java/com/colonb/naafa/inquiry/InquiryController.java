package com.colonb.naafa.inquiry;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.inquiry.dto.InquiryDto;
import com.colonb.naafa.result.Result;
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
@RequiredArgsConstructor
@RequestMapping("inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("auth/add")
    public ResponseEntity<Result> add(@RequestBody @Valid InquiryDto req,
                                             BindingResult bindingResul,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST , false), HttpStatus.BAD_REQUEST);
        }
        Result res = inquiryService.add(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
