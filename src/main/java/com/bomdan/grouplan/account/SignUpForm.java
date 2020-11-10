package com.bomdan.grouplan.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpForm {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password1;
    @NotBlank
    private String password2;
}
