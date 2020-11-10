package com.bomdan.grouplan.account;

import com.bomdan.grouplan.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account save(Account account);
    Optional<Account> findByEmail(String email);

}
