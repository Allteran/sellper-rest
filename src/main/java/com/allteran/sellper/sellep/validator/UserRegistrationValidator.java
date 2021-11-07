package com.allteran.sellper.sellep.validator;

import com.allteran.sellper.sellep.domain.User;
import com.allteran.sellper.sellep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserRegistrationValidator implements Validator {
    @Value("${validator.empty-field}")
    private String EMPTY_FIELD_ERROR;
    @Value("${validator.phone}")
    private String PHONE_ERROR;
    @Value("${validator.phone-exist}")
    private String PHONE_EXIST_ERROR;
    @Value("${validator.password-length}")
    private String PASSWORD_LENGTH_ERROR;
    @Value("${validator.password-confirm}")
    private String PASSWORD_CONFIRM_ERROR;

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", EMPTY_FIELD_ERROR);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", EMPTY_FIELD_ERROR);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", EMPTY_FIELD_ERROR);
        if (user.getPhone().length() != 11) {
            errors.rejectValue("phone", PHONE_ERROR);
        }
        /**
         * Next line uses regex to check if input string matches with special regex pattern
         * \ - means that it's gonna be regular expresion, so wait for the pattern
         * \^ - means that it will starts from new line
         * ?(79) - means that at the begining gonna be two numbers "79"
         * \d - means that next text is digits
         * {9} - means that it gonna be only 9 number of digits, no more or less
         */
        if (!user.getPhone().matches("\\^?(79)\\d{9}")) {
            errors.rejectValue("phone", PHONE_ERROR);
        }

        User userByPhone = userService.findByPhone(user.getPhone());
        if (userByPhone != null) {
            errors.rejectValue("phone", PHONE_EXIST_ERROR);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", EMPTY_FIELD_ERROR);
        if (user.getPassword().length() < 8 || user.getPassword().length() >= 32) {
            errors.rejectValue("password", PASSWORD_LENGTH_ERROR);
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", EMPTY_FIELD_ERROR);
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            errors.rejectValue("password", PASSWORD_CONFIRM_ERROR);
            errors.rejectValue("passwordConfirm", PASSWORD_CONFIRM_ERROR);
        }
    }
}
