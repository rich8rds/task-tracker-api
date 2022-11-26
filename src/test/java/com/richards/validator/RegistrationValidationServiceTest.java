package com.richards.validator;

import com.richards.dto.RegisterDto;
import com.richards.enums.ValidationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static com.richards.validator.RegistrationValidationService.*;

@RunWith(SpringRunner.class)
@SpringBootTest

class RegistrationValidationServiceTest {
  RegisterDto registerDto;

    @BeforeEach
    public void before() throws Exception {
        registerDto = new RegisterDto();
//        registerDto = RegisterDto.builder()
//                .firstName("Richards")
//                .lastName("Kohl")
//                .email("richardKohl@gmail.com")
//                .password("indigo&violet")
//                .gender(Gender.MALE)
//                .build();
    }

    @Test
    @DisplayName("VALIDATE FIRSTNAME: should return FIRSTNAME_NOT_VALID")
    public void firstNameNotValid() {
        registerDto.setFirstName("Richards!!");
        ValidationResult result = isFirstNameValid().apply(registerDto);
        assertEquals(ValidationResult.FIRSTNAME_NOT_VALID, result);
    }

    @Test
    @DisplayName("VALIDATE FIRSTNAME: Should return SUCCESS")
    public void firstNameValid() {
        registerDto.setFirstName("Richards");
        ValidationResult result = isFirstNameValid().apply(registerDto);
        assertEquals(ValidationResult.SUCCESS, result);
    }

    @Test
    @DisplayName("VALIDATE LASTNAME: should return LASTNAME_NOT_VALID")
    public void lastNameNotValid() {
        registerDto.setLastName("Richards!!");
        ValidationResult result = isSurnameValid().apply(registerDto);
        assertEquals(ValidationResult.SURNAME_NOT_VALID, result);
    }

    @Test
    @DisplayName("VALIDATE LASTNAME: Should return SUCCESS")
    public void lastNameValid() {
        registerDto.setLastName("Richards");
        ValidationResult result = isSurnameValid().apply(registerDto);
        assertEquals(ValidationResult.SUCCESS, result);
    }

    @Test
    @DisplayName("VALIDATE PASSWORD: " +
            "Should return PASSWORD_MUST_BE_7_OR_MORE_CHARACTERS_LONG")
    public void passwordNotValid() {
        registerDto.setPassword("ioejfk");
        ValidationResult result = isPasswordValid().apply(registerDto);
        assertEquals(ValidationResult.PASSWORD_MUST_BE_8_OR_MORE_CHARACTERS_LONG, result);

    }

    @Test
    @DisplayName("VALIDATE PASSWORD: Should return SUCCESS")
    public void passwordValid() {
        registerDto.setPassword("ioejf%%%%%%k");
        ValidationResult result = isPasswordValid().apply(registerDto);
        assertEquals(ValidationResult.SUCCESS, result);

    }

    @Test
    @DisplayName("VALIDATE EMAIL: Should return SUCCESS")
    public void emailValid() {
        registerDto.setEmail("richards@gmail.com");
        ValidationResult result = isEmailValid().apply(registerDto);
        assertEquals(ValidationResult.SUCCESS, result);
    }

    @Test
    @DisplayName("VALIDATE EMAIL: Should return SUCCESS")
    public void emailNotValid() {
        registerDto.setEmail("richardsgmail.com");
        ValidationResult result = isEmailValid().apply(registerDto);
        assertEquals(ValidationResult.EMAIL_NOT_VALID, result);
    }
}