package com.bomdan.grouplan.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/login")
    public String loginPage(){
        return "account/utility-login";
    }
}
