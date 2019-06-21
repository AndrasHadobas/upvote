package hu.ponte.upvote.validator;

import hu.ponte.upvote.domain.Account;
import hu.ponte.upvote.dto.IdeaCreationCommand;
import hu.ponte.upvote.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IdeaCreationCommandValidator implements Validator {

    private static Logger logger = LoggerFactory.getLogger(AccountRegistrationCommandValidator.class);

    private AccountRepository accountRepository;

    @Autowired
    public IdeaCreationCommandValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return IdeaCreationCommand.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target != null) {

            IdeaCreationCommand ideaCreationCommand= (IdeaCreationCommand) target;

            rejectIfStringIsEmpty(errors, ideaCreationCommand.getName(),
                    "name",
                    "IdeaCreationCommand (name) can not be empty!",
                    "ideaCreationCommand.name.isEmpty");

            rejectIfStringIsEmpty(errors, ideaCreationCommand.getDescription(),
                    "description",
                    "IdeaCreationCommand (description) can not be empty!",
                    "ideaCreationCommand.description.isEmpty");

            Account registeredAccountWithUserName = accountRepository.findByUserName(ideaCreationCommand.getAccountUserName());
            rejectIfDoesntExist(errors, registeredAccountWithUserName,
                    "accountUserName",
                    "IdeaCreationCommand (accountUserName) doesn't exist!",
                    "ideaCreationCommand.accountUserName.doesnt.exist");
        }
    }

    private void rejectIfStringIsEmpty(Errors errors, String field, String fieldName, String logMessage, String errorMessage) {
        if (field == null || field.trim().equals("")) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }

    private void rejectIfDoesntExist(Errors errors, Object field, String fieldName, String logMessage, String errorMessage) {
        if (field == null) {
            logger.error(logMessage);
            errors.rejectValue(fieldName, errorMessage);
        }
    }
}
