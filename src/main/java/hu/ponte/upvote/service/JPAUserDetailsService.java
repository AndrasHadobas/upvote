package hu.ponte.upvote.service;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JPAUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    @Autowired
    public JPAUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(userName);

        if (account == null) {
            throw new UsernameNotFoundException("No account found with name: " + userName);
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(account.getRole().toString());

        return User.withUsername(userName).authorities(authorities).password(account.getPassword()).build();
    }
}
