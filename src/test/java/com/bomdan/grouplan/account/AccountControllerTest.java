package com.bomdan.grouplan.account;

import com.bomdan.grouplan.domain.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntityManager em;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    void afterEach(){
        em.clear();
    }

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
        ResultActions perform = signUp();

        //then
        perform.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @DisplayName("회원가입 이후 데이터 값 확인")
    public void signUp_dataCheck() throws Exception{
        //given
        String email = "bomin@naver.com";
        String nickname = "bomin";
        signUp();
        //then

        Account account = accountRepository.findByEmail(email).orElseThrow();

        Assertions.assertThat(account.getEmail()).isEqualTo(email);
        Assertions.assertThat(account.getNickname()).isEqualTo(nickname);
    }

    @Test
    @DisplayName("회원가입시 패스워드 인코딩 확인")
    public void signUp_encodingPassword() throws Exception{
        //given
        String email = "bomin@naver.com";
        String password = "1234";
        //when
        signUp();
        //then
        Account account = accountRepository.findByEmail(email).orElseThrow();
        Assertions.assertThat(account.getPassword()).isNotEqualTo(password);
    }

    @Test
    @DisplayName("회원가입 이후 로그인 확인")
    public void signUp_andLogIn() throws Exception{
        //given
        //when
        ResultActions resultActions = signUp();
        //then
        resultActions.andExpect(authenticated());
    }

    @Test
    @DisplayName("로그인 실패")
    public void login_fail() throws Exception{
        //given
        String email = "bomin@naver.com";
        String password = "1234";

        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .param("username", email)
                .param("password", password)
                .with(csrf()));
        //then
        perform.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @DisplayName("로그인 성공")
    public void login_completed() throws Exception{
        //given
        String email = "bomin@naver.com";
        String password = "1234";
        signUp();
        SecurityContextHolder.clearContext();
        //when
        ResultActions perform = mockMvc.perform(post("/login")
                .param("username", email)
                .param("password", password)
                .with(csrf()));
        //then
        perform.andExpect(status().isOk())
                .andExpect(authenticated());
    }

    private ResultActions signUp() throws Exception{
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
         return mockMvc.perform(post(api)
                .param("email", signUpForm.getEmail())
                .param("nickname", signUpForm.getNickname())
                .param("password1", signUpForm.getPassword1())
                .param("password2", signUpForm.getPassword2())
                .with(csrf())
        );
    }
}