package com.bomdan.grouplan;

import com.bomdan.grouplan.account.AccountResponseForm;
import com.bomdan.grouplan.domain.Account;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeView(@CurrentUser Account account, Model model){
        if(account != null) model.addAttribute("account", new AccountResponseForm(account));
        return "index";
    }
}
