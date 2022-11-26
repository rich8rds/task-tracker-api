package com.richards.validator;


import com.richards.dto.RegisterDto;
import com.richards.enums.ValidationResult;
import com.richards.repository.RegistrationRepository;

import java.util.function.Function;
import java.util.regex.Pattern;

import static com.richards.enums.ValidationResult.*;

public interface RegistrationValidationService extends Function<RegisterDto, ValidationResult> {
    String REGEX = "^[a-zA-Z]+([a-zA-Z]+)+$";
    String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    static RegistrationValidationService isFirstNameValid() {
        return user -> user.getFirstName() != null &&
                Pattern.matches(REGEX, user.getFirstName()) ?
                SUCCESS : FIRSTNAME_NOT_VALID;
    }

    static RegistrationValidationService isSurnameValid() {
        return user -> user.getLastName() != null &&
                Pattern.matches(REGEX, user.getLastName()) ?
                SUCCESS : SURNAME_NOT_VALID;
    }

    static RegistrationValidationService isPasswordValid() {
        return user -> user.getPassword() != null && user.getPassword().length() >= 7 ?
                SUCCESS : PASSWORD_MUST_BE_8_OR_MORE_CHARACTERS_LONG;
    }

    static RegistrationValidationService isEmailValid() {
        return user -> user.getEmail() != null &&
                Pattern.matches(EMAIL_REGEX, user.getEmail()) ?
                SUCCESS : EMAIL_NOT_VALID;
    }

    static RegistrationValidationService emailExists(RegistrationRepository registrationRepository) {
        return user ->  registrationRepository.findByEmail(user.getEmail()).isEmpty() ? SUCCESS : EMAIL_ALREADY_EXISTS;
    }

    static RegistrationValidationService isGenderValid() {
        return user -> user.getGender() != null ? SUCCESS : GENDER_NOT_VALID;
    }

    default RegistrationValidationService and (RegistrationValidationService other) {
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }
}