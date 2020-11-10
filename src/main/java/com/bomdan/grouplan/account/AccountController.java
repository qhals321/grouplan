package com.bomdan.grouplan.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AccountController {

    @InitBinder("signUpForm")
    private void initBinding(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new SignUpFormValidator());
    }

    @GetMapping("/login")
    public String loginPage(){
        return "account/utility-login";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model){
        model.addAttribute(new SignUpForm());
        return "account/utility-create-account";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "account/utility-create-account";
        }
        return "redirect:/";
    }
}
