package com.colonb.naafa.inquiry;

import com.colonb.naafa.auth.UserDetailsImpl;
import com.colonb.naafa.inquiry.dto.InquiryAnswerDto;
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
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
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

    @GetMapping("auth/list")
    public ResponseEntity<Result> list(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = inquiryService.list(userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }

    @GetMapping("auth/view/{seq}")
    public ResponseEntity<Result> view(@PathVariable long seq,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Result res = inquiryService.view(seq, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }


    // TODO 기획에서 문의사항 답변하는 어드민페이지 생기면 패키저 이동 고민중
    @PostMapping("auth/answer/add/{seq}")
    public ResponseEntity<Result> answerAdd(@RequestBody @Valid InquiryAnswerDto req,
                                            BindingResult bindingResul, @PathVariable long seq,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (bindingResul.hasErrors()) {
            return new ResponseEntity<>(new Result(bindingResul.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST, false), HttpStatus.BAD_REQUEST);
        }
        Result res = inquiryService.answerAdd(req, seq, userDetails);
        return ResponseEntity.status(res.status()).body(res);
    }
}
