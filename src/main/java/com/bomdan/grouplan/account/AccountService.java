package com.bomdan.grouplan.account;

import com.bomdan.grouplan.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account signUpNewAccount(SignUpForm signUpForm){
        signUpForm.setPassword1(passwordEncoder.encode(signUpForm.getPassword1()));
        Account savedAccount = accountRepository.save(Account.createAccount(signUpForm));
        loginAfterSignUp(savedAccount);     //로그인 처리
        return savedAccount;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account findAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("wrong email"));
        return new UserAccount(findAccount);
    }

    private void loginAfterSignUp(Account account){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(new UserAccount(account), null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }
}
