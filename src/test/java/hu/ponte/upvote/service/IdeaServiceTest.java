package hu.ponte.upvote.service;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.domain.Idea;
import hu.ponte.upvote.dto.IdeaCreationCommand;
import hu.ponte.upvote.repository.AccountRepository;
import hu.ponte.upvote.repository.IdeaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdeaServiceTest {

    private AccountRepository accountRepositoryMock;

    private IdeaRepository ideaRepositoryMock;

    private IdeaService ideaService;

    @BeforeEach
    public void setUp() {
        accountRepositoryMock = mock(AccountRepository.class);
        ideaRepositoryMock = mock(IdeaRepository.class);

        ideaService = new IdeaService(accountRepositoryMock, ideaRepositoryMock);
    }

    @Test
    public void testSaveIdeaWhenAccountUserNameIsGiven() {
        Account account = new Account();
        account.setUserName("andras.hadobas");

        IdeaCreationCommand ideaCreationCommand = new IdeaCreationCommand();
        ideaCreationCommand.setAccountUserName("andras.hadobas");
        ideaCreationCommand.setDescription("ideaDescription");
        ideaCreationCommand.setName("ideaName");

        when(accountRepositoryMock.findByUserName(any(String.class))).thenReturn(account);
        when(ideaRepositoryMock.save(any(Idea.class))).thenAnswer(returnsFirstArg());

        Idea savedIdea = ideaService.saveIdea(ideaCreationCommand);

        assertEquals("ideaName", savedIdea.getName());
        assertEquals("ideaDescription", savedIdea.getDescription());
    }

    @Test
    public void testSaveIdeaWhenAccountUserNameIsNotGiven() {
        Account account = new Account();
        account.setUserName("andras.hadobas");

        IdeaCreationCommand ideaCreationCommand = new IdeaCreationCommand();
        ideaCreationCommand.setDescription("ideaDescription");
        ideaCreationCommand.setName("ideaName");

        when(ideaRepositoryMock.save(any(Idea.class))).thenAnswer(returnsFirstArg());

        assertThrows(EntityNotFoundException.class, () -> ideaService.saveIdea(ideaCreationCommand));
    }
}
