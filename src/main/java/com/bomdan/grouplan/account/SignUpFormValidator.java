package com.bomdan.grouplan.account;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SignUpFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SignUpForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpForm signUpForm = (SignUpForm) target;
        String password1 = signUpForm.getPassword1();
        String password2 = signUpForm.getPassword2();
        if(!password1.equals(password2)){
            errors.rejectValue("password2","password2.notSame", "wrong password");
        }
    }
}
