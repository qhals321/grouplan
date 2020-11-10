package com.bomdan.grouplan.account;

import com.bomdan.grouplan.domain.Account;
import lombok.Data;

@Data
public class AccountResponseForm {
    private String email;
    private String nickname;

    public AccountResponseForm(Account account) {
        this.email = account.getEmail();
        this.nickname = account.getNickname();
    }
}
