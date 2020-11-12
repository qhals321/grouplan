package com.bomdan.grouplan.account;

import com.bomdan.grouplan.CurrentUser;
import com.bomdan.grouplan.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

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

    //TODO 화면단에서 닉네임과 이메일 중복체크하기
    @PostMapping("/signUp")
    public String signUp(@Valid SignUpForm signUpForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "account/utility-create-account";
        }
        accountService.signUpNewAccount(signUpForm);

        return "redirect:/";
    }

    @GetMapping("account/profile")
    public String profileView(@CurrentUser Account account, Model model){
        if(account!=null) model.addAttribute("account", new AccountConfigureResponseForm(account));
        return "account/utility-account-settings";
    }
}
