package com.colonb.naafa.user;

import com.colonb.naafa.auth.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("")
    String main(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        if (Objects.nonNull(userDetails)) {
            model.addAttribute("username", userDetails.getUsername());
        } else {
            model.addAttribute("username","none");
        }
        return "index";
    }
}
