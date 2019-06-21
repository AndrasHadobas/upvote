package hu.ponte.upvote.controller;

import hu.ponte.upvote.dto.IdeaCreationCommand;
import hu.ponte.upvote.dto.IdeaListItem;
import hu.ponte.upvote.dto.IdeaListItemForAdmin;
import hu.ponte.upvote.service.IdeaService;
import hu.ponte.upvote.validator.IdeaCreationCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    private static final Logger logger = LoggerFactory.getLogger(IdeaController.class);

    private IdeaService ideaService;

    private IdeaCreationCommandValidator ideaCreationCommandValidator;

    @Autowired
    public IdeaController(IdeaService ideaService, IdeaCreationCommandValidator ideaCreationCommandValidator) {
        this.ideaService = ideaService;
        this.ideaCreationCommandValidator = ideaCreationCommandValidator;
    }

    @InitBinder("ideaCreationCommand")
    protected void initIdeaBinder(WebDataBinder binder) {
        binder.addValidators(ideaCreationCommandValidator);
    }

    @PostMapping
    public ResponseEntity createIdea(@RequestBody @Valid IdeaCreationCommand ideaCreationCommand, Principal principal) {
        ideaService.saveIdea(ideaCreationCommand);
        logger.info("user:{}: Idea is created!", principal.getName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("allApprovedIdeas")
    public ResponseEntity<List<IdeaListItem>> getAllApprovedIdeas(Principal principal) {
        List<IdeaListItem> ideaListItems = ideaService.getAllApprovedIdeas();
        logger.info("user:{}: All Approved Ideas have been listed for USER!", principal.getName());
        return new ResponseEntity<>(ideaListItems, HttpStatus.OK);
    }

    @PostMapping("{id}/addVote")
    public ResponseEntity addVote(@PathVariable Long id, HttpServletRequest request, Principal principal) {

        HttpSession session = request.getSession();

        ideaService.addVote(id, session, principal);

        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/allApprovedIdeasForAdmin")
    public ResponseEntity<List<IdeaListItemForAdmin>> getAllApprovedIdeasForAdmin(Principal principal) {
        List<IdeaListItemForAdmin> ideaListItemsForAdmin = ideaService.getAllApprovedIdeasForAdmin();
        logger.info("user:{}: All Approved Ideas have been listed for ADMIN!", principal.getName());
        return new ResponseEntity<>(ideaListItemsForAdmin, HttpStatus.OK);
    }

    @GetMapping("/allUnApprovedIdeasForAdmin")
    public ResponseEntity<List<IdeaListItemForAdmin>> getAllUnApprovedIdeasForAdmin(Principal principal) {
        List<IdeaListItemForAdmin> ideaListItemsForAdmin = ideaService.getAllUnApprovedIdeasForAdmin();
        logger.info("user:{}: All UnApproved Ideas have been listed for ADMIN!", principal.getName());
        return new ResponseEntity<>(ideaListItemsForAdmin, HttpStatus.OK);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity approve(@PathVariable Long id, Principal principal) {
        ideaService.approveIdea(id);
        logger.info("user:{}: ADMIN Approved {}.id Idea!", principal.getName(), id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity reject(@PathVariable Long id, Principal principal) {
        ideaService.rejectIdea(id);
        logger.info("user:{}: ADMIN Rejected {}.id Idea!", principal.getName(), id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
