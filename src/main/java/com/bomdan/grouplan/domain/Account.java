package com.bomdan.grouplan.domain;

import com.bomdan.grouplan.account.SignUpForm;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String emailToken;


    public static Account createAccount(SignUpForm signUpForm){
        Account account = new Account();
        account.email = signUpForm.getEmail();
        account.nickname = signUpForm.getNickname();
        account.password = signUpForm.getPassword1();
        return account;
    }


}
