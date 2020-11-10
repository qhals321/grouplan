package com.bomdan.grouplan.account;

import lombok.Data;

@Data
public class SignUpForm {
    private String email;
    private String nickname;
    private String password1;
    private String password2;
}
