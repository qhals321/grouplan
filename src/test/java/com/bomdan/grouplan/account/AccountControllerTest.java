package com.bomdan.grouplan.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("로그인 뷰: 로그인 화면 보여주기")
    public void loginViewAccess() throws Exception{
        //given
        String api = "/login";
        //when
        ResultActions perform = mockMvc.perform(get(api));
        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 뷰: 회원가입 화면 보여주기 성공")
    public void signUpViewAccess() throws Exception{
        //given
        String api = "/signUp";
        //when
        ResultActions perform = mockMvc.perform(get(api));
        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 : 비밀번호1과 비밀번호2가 다름")
    public void signUpFail_wrongPassword() throws Exception{
        //given
        String api = "/signUp";

        String password1 = "1234";
        String password2 = "12345";
        String email = "bomin@naver.com";
        String nickname = "bomin";

        SignUpForm signUpForm = SignUpForm.builder()
                .email(email)
                .nickname(nickname)
                .password1(password1)
                .password2(password2)
                .build();
        //when
        ResultActions perform = mockMvc.perform(post(api)
                .param("email", signUpForm.getEmail())
                .param("nickname", signUpForm.getNickname())
                .param("password1", signUpForm.getPassword1())
                .param("password2", signUpForm.getPassword2())
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(view().name("account/utility-create-account"));
    }

    @Test
    @DisplayName("회원가입 실패 : 닉네임 값이 비어있음")
    public void signUpFail_blankNickname() throws Exception{
        //given
        String api = "/signUp";

        String password1 = "1234";
        String password2 = "1234";
        String email = "bomin@naver.com";

        SignUpForm signUpForm = SignUpForm.builder()
                .email(email)
                .password1(password1)
                .password2(password2)
                .build();
        //when
        ResultActions perform = mockMvc.perform(post(api)
                .param("email", signUpForm.getEmail())
                .param("password1", signUpForm.getPassword1())
                .param("password2", signUpForm.getPassword2())
                .with(csrf())
        );
        //then
        perform.andExpect(status().isOk())
                .andExpect(view().name("account/utility-create-account"));
    }

    @Test
    @DisplayName("회원가입 성공")
    public void signUp_success() throws Exception{
        //given
        String api = "/signUp";

        String password1 = "1234";
        String password2 = "1234";
        String email = "bomin@naver.com";
        String nickname = "bomin";

        SignUpForm signUpForm = SignUpForm.builder()
                .email(email)
                .nickname(nickname)
                .password1(password1)
                .password2(password2)
                .build();

        //when
        ResultActions perform = mockMvc.perform(post(api)
                .param("email", signUpForm.getEmail())
                .param("nickname", signUpForm.getNickname())
                .param("password1", signUpForm.getPassword1())
                .param("password2", signUpForm.getPassword2())
                .with(csrf())
        );
        //then
        perform.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}