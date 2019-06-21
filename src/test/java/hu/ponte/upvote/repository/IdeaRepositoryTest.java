package hu.ponte.upvote.repository;

import hu.ponte.upvote.config.SpringWebConfig;
import hu.ponte.upvote.config.TestConfiguration;
import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.domain.Idea;
import hu.ponte.upvote.domain.Role;
import hu.ponte.upvote.domain.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebConfig.class, TestConfiguration.class})
public class IdeaRepositoryTest {

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        ideaRepository.deleteAll();
    }

    @Test
    public void testSaveAndFindAllUnApprovedIdeas() {

        Account account = new Account();
        account.setRole(Role.ROLE_USER);
        account.setFirstName("Andras");
        account.setLastName("Hadobas");
        account.setUserName("andras.hadobas");
        account.setEmail("andras.hadobas@gmail.com");
        account.setPassword("AH2019@progmasters");
        accountRepository.save(account);

        Idea idea1 = new Idea();
        idea1.setName("idea1Name");
        idea1.setVote(0L);
        idea1.setDescription("idea1Description");
        idea1.setCreatedAt(LocalDateTime.now());
        idea1.setStatus(Status.STATUS_WAITING_FOR_ACCEPTANCE);
        idea1.setAccount(account);
        ideaRepository.save(idea1);

        Idea idea2 = new Idea();
        idea2.setName("idea2Name");
        idea2.setVote(0L);
        idea2.setDescription("idea2Description");
        idea2.setCreatedAt(LocalDateTime.now());
        idea2.setStatus(Status.STATUS_WAITING_FOR_ACCEPTANCE);
        idea2.setAccount(account);
        ideaRepository.save(idea2);

        List<Idea> ideaList = ideaRepository.getAllUnApprovedIdeas();
        Idea testIdea = ideaList.get(1);

        assertEquals(2, ideaList.size());
        assertEquals(testIdea.getDescription(), "idea2Description");
    }
}
