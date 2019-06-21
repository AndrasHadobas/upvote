package hu.ponte.upvote.controller;

import hu.ponte.upvote.dto.AccountRegistrationCommand;
import hu.ponte.upvote.security.AuthenticatedUserDetails;
import hu.ponte.upvote.service.AccountService;
import hu.ponte.upvote.validator.AccountRegistrationCommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController{

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private AccountService accountService;

    private AccountRegistrationCommandValidator accountRegistrationCommandValidator;

    @Autowired
    public AccountController(AccountService accountService, AccountRegistrationCommandValidator accountRegistrationCommandValidator) {
        this.accountService = accountService;
        this.accountRegistrationCommandValidator = accountRegistrationCommandValidator;
    }

    @InitBinder("accountRegistrationCommand")
    protected void initAccountBinder(WebDataBinder binder) {
        binder.addValidators(accountRegistrationCommandValidator);
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody @Valid AccountRegistrationCommand accountRegistrationCommand) {
        accountService.saveAccount(accountRegistrationCommand);
        logger.info("Account is created!");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        logger.info("user:{}: Account is logged in!", principal.getName());

        return new ResponseEntity<>(new AuthenticatedUserDetails(user), HttpStatus.ACCEPTED);
    }
}
