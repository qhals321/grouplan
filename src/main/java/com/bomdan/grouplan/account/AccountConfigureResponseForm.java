package com.bomdan.grouplan.account;

import com.bomdan.grouplan.domain.Account;
import lombok.Data;

@Data
public class AccountConfigureResponseForm {
    private String email;
    private String nickname;
    private String bio;

    public AccountConfigureResponseForm(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
        this.bio = account.getBio();
    }
}
