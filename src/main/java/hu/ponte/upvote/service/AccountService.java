package hu.ponte.upvote.service;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.dto.AccountRegistrationCommand;
import hu.ponte.upvote.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

    private AccountRepository accountRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account saveAccount(AccountRegistrationCommand accountRegistrationCommand) {
        Account account = new Account(accountRegistrationCommand);

        String encodedPassword = passwordEncoder.encode(accountRegistrationCommand.getPassword());
        account.setPassword(encodedPassword);

        return accountRepository.save(account);
    }
}
