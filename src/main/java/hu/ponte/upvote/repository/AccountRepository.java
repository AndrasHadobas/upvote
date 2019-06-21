package hu.ponte.upvote.repository;

import hu.ponte.upvote.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUserName(String userName);

    Account findByEmail(String email);
}
