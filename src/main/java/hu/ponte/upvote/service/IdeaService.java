package hu.ponte.upvote.service;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.domain.Idea;
import hu.ponte.upvote.domain.Status;
import hu.ponte.upvote.dto.IdeaCreationCommand;
import hu.ponte.upvote.dto.IdeaListItem;
import hu.ponte.upvote.dto.IdeaListItemForAdmin;
import hu.ponte.upvote.repository.AccountRepository;
import hu.ponte.upvote.repository.IdeaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IdeaService {

    private static final Logger logger = LoggerFactory.getLogger(IdeaService.class);

    private AccountRepository accountRepository;

    private IdeaRepository ideaRepository;

    @Autowired
    public IdeaService(AccountRepository accountRepository, IdeaRepository ideaRepository) {
        this.accountRepository = accountRepository;
        this.ideaRepository = ideaRepository;
    }

    //for account:USER
    public Idea saveIdea(IdeaCreationCommand ideaCreationCommand) {
        Account account = accountRepository.findByUserName(ideaCreationCommand.getAccountUserName());
        if (account != null) {
            Idea idea = new Idea(ideaCreationCommand, account);
            return ideaRepository.save(idea);
        } else {
            throw new EntityNotFoundException("Create error, no Account was found " +
                    "with this userName: " + ideaCreationCommand.getAccountUserName());
        }
    }

    //for account:USER
    public List<IdeaListItem> getAllApprovedIdeas() {
        List<Idea> ideas = ideaRepository.getAllApprovedIdeas();
        return getIdeaListItems(ideas);
    }

    //for account:USER
    public List<IdeaListItem> getAllApprovedIdeasByAccount(Account myAccount) {
        List<Idea> ideas = ideaRepository.getAllApprovedIdeasByAccount(myAccount);
        return getIdeaListItems(ideas);
    }

    //for account:USER
    public List<IdeaListItem> getAllApprovedIdeasExceptAccount(Account myAccount) {
        List<Idea> ideas = ideaRepository.getAllApprovedIdeasExceptAccount(myAccount);
        return getIdeaListItems(ideas);
    }

    private List<IdeaListItem> getIdeaListItems(List<Idea> ideas) {
        List<IdeaListItem> ideaListItems = new ArrayList<>();
        for (Idea idea : ideas) {
            ideaListItems.add(new IdeaListItem(idea));
        }
        return ideaListItems;
    }

    //for account:USER
    public void addVote(Long id, HttpSession session, Principal principal) {

        //account can vote once in a session
        //account can vote on only approved idea

        if (session.getAttribute("votingAbility") == null) {

            Optional<Idea> optionalIdea = ideaRepository.findById(id);

            if (optionalIdea.isPresent()) {

                Idea foundIdea = optionalIdea.get();

                if (foundIdea.getStatus() == Status.STATUS_APPROVED) {
                    Long votes = foundIdea.getVote();
                    Long updatedVote = votes + 1L;
                    foundIdea.setVote(updatedVote);
                    ideaRepository.save(foundIdea);
                    session.setAttribute("votingAbility", false);
                    logger.info("user:{}: successfully added 1 vote for {}.id Idea!", principal.getName(), id);
                } else {
                    logger.info("user:{}: tried to vote for UnApproved idea, it's a CHEAT!", principal.getName());
                }
            } else {
                throw new EntityNotFoundException("Vote error, there is no idea with the given id: " + id);
            }
        } else {
            logger.info("user:{}: has already voted!", principal.getName());
        }
    }

    //for account:ADMIN
    public List<IdeaListItemForAdmin> getAllApprovedIdeasForAdmin() {
        List<Idea> ideas = ideaRepository.getAllApprovedIdeas();
        return getIdeaListItemsForAdmin(ideas);
    }

    //for account:ADMIN
    public List<IdeaListItemForAdmin> getAllUnApprovedIdeasForAdmin() {
        List<Idea> ideas = ideaRepository.getAllUnApprovedIdeas();
        return getIdeaListItemsForAdmin(ideas);
    }

    private List<IdeaListItemForAdmin> getIdeaListItemsForAdmin(List<Idea> ideas) {
        List<IdeaListItemForAdmin> ideaListItemsForAdmin = new ArrayList<>();
        for (Idea idea : ideas) {
            ideaListItemsForAdmin.add(new IdeaListItemForAdmin(idea));
        }
        return ideaListItemsForAdmin;
    }

    //for account:ADMIN
    public void approveIdea(Long id) {
        Optional<Idea> optionalIdea = ideaRepository.findById(id);

        if (optionalIdea.isPresent()) {
            Idea idea = optionalIdea.get();
            Status ideaStatus = idea.getStatus();
            if (ideaStatus == Status.STATUS_WAITING_FOR_ACCEPTANCE) {
                idea.setStatus(Status.STATUS_APPROVED);
                ideaRepository.save(idea);
            }
        } else {
            throw new EntityNotFoundException("Approving error, there is no idea with the given id: " + id);
        }
    }

    //for account:ADMIN
    public void rejectIdea(Long id) {
        Optional<Idea> optionalIdea = ideaRepository.findById(id);

        if (optionalIdea.isPresent()) {
            Idea idea = optionalIdea.get();
            ideaRepository.delete(idea);
        } else {
            throw new EntityNotFoundException("Rejecting error, there is no idea with the given id: " + id);
        }
    }
}
