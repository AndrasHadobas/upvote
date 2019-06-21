package hu.ponte.upvote.validator;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.dto.AccountRegistrationCommand;
import hu.ponte.upvote.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class AccountRegistrationCommandValidator implements Validator {

    private static Logger logger = LoggerFactory.getLogger(AccountRegistrationCommandValidator.class);

    private AccountRepository accountRepository;

    private final Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    @Autowired
    public AccountRegistrationCommandValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRegistrationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target != null) {

            AccountRegistrationCommand accountRegistrationCommand = (AccountRegistrationCommand) target;

            rejectIfStringNotSetAndFirstLetterIsNotUppercase(errors, accountRegistrationCommand.getFirstName(),
                    "firstName",
                    "AccountRegistrationCommand (firstName) is not given correctly!",
                    "accountRegistrationCommand.firstName.notGiven");
            rejectIfStringNotSetAndFirstLetterIsNotUppercase(errors, accountRegistrationCommand.getLastName(),
                    "lastName",
                    "AccountRegistrationCommand (lastName) is not given correctly!",
                    "accountRegistrationCommand.lastName.notGiven");

            Account registeredAccountWithUserName = accountRepository.findByUserName(accountRegistrationCommand.getUserName());
            rejectIfAlreadyExists(errors, registeredAccountWithUserName,
                    "userName",
                    "AccountRegistrationCommand (userName) already exists!",
                    "accountRegistrationCommand.userName.already.exists");

            rejectIfStringLengthIsTooShortOrTooLong(errors, accountRegistrationCommand.getUserName(),
                    "userName",
                    "AccountRegistrationCommand (userName) is too short or too long!",
                    "accountRegistrationCommand.userName.isTooShort.or.tooLong");

            Account registeredAccountWithEmail = accountRepository.findByEmail(accountRegistrationCommand.getEmail());
            rejectIfAlreadyExists(errors, registeredAccountWithEmail,
                    "email",
                    "AccountRegistrationCommand (email) already exists!",
                    "accountRegistrationCommand.email.already.exists");

            rejectIfStringIsEmpty(errors, accountRegistrationCommand.getEmail(),
                    "email",
                    "AccountRegistrationCommand (email) can not be empty!",
                    "accountRegistrationCommand.email.isEmpty");

            rejectIfStringDoesNotMatchAPattern(errors, accountRegistrationCommand.getPassword(),
                    "password",
                    passwordPattern,
                    "AccountRegistrationCommand (password) is not valid!",
                    "accountRegistrationCommand.password.isNotValid");

            rejectIfTwoStringsAreTheSame(errors,
                    accountRegistrationCommand.getPassword(),
                    accountRegistrationCommand.getRetypedPassword(),
                    "password",
                    "AccountRegistrationCommand (password-retypedPassword) do not match!",
                    "accountRegistrationCommand.password.and.retypedPassword.do.not.match");
        }
    }

    private void rejectIfStringNotSetAndFirstLetterIsNotUppercase(Errors errors, String field, String fieldName, String logMessage, String errorMessage) {
        if (field == null || field.trim().equals("") || !Character.isUpperCase(field.charAt(0))) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfAlreadyExists(Errors errors, Object field, String fieldName, String logMessage, String errorMessage) {
        if (field != null) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfStringIsEmpty(Errors errors, String field, String fieldName, String logMessage, String errorMessage) {
        if (field == null || field.trim().equals("")) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfStringLengthIsTooShortOrTooLong(Errors errors, String field, String fieldName, String logMessage, String errorMessage) {
        if (field == null || field.trim().equals("") || field.length() < 4 || field.length() > 30) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfTwoStringsAreTheSame(Errors errors, String fieldOne, String fieldTwo, String fieldName, String logMessage, String errorMessage) {
        if (fieldOne == null || fieldTwo == null || fieldOne.trim().equals("") || fieldTwo.trim().equals("") || !fieldOne.equals(fieldTwo)) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfStringDoesNotMatchAPattern(Errors errors, String field, String fieldName, Pattern pattern, String logMessage, String errorMessage) {
        if (field == null || !pattern.matcher(field).matches()) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }
}
