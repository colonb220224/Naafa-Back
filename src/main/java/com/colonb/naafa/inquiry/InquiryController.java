package com.colonb.naafa.inquiry;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.inquiry.dto.InquiryDto;
import com.colonb.naafa.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @PostMapping("auth/add")
    public ResponseEntity<Result> add(@RequestBody @Valid InquiryDto req,
                                             BindingResult bindingResul,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST , false), HttpStatus.BAD_REQUEST);
        }
        Result res = inquiryService.add(req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @PostMapping("auth/modify/{seq}")
    public ResponseEntity<Result> modify(@RequestBody @Valid InquiryDto req,
                                                BindingResult bindingResul,
                                                @PathVariable long seq,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST , false), HttpStatus.BAD_REQUEST);
        }
        Result res = inquiryService.modify(seq, req, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @DeleteMapping("auth/remove/{seq}")
    public ResponseEntity<Result> remove(@PathVariable long seq,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = inquiryService.remove(seq, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
