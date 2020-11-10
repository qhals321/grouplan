package com.bomdan.grouplan.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/login")
    public String loginPage(){
        return "account/utility-login";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model){
        model.addAttribute(new SignUpForm());
        return "account/utility-create-account";
    }
}
