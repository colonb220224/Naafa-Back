package com.colonb.naafa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
}
