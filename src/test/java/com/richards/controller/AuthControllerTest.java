package com.richards.controller;

import com.richards.dto.RegisterDto;
import com.richards.entity.User;
import com.richards.enums.Gender;
import com.richards.repository.RegistrationRepository;
import com.richards.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@RequiredArgsConstructor
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private RegistrationRepository registrationRepository;

    private User user;

    @BeforeEach
    public void before() throws Exception {
        user = User.builder()
                .id(465L)
                .firstName("Richards")
                .lastName("Kohl")
                .email("richardKohl@gmail.com")
                .password("indigo&violet")
                .gender(Gender.MALE)
                .createdAt(new Date())
                .build();
    }

    @Test
    public void  registerNewUser() {
        RegisterDto registerDto = RegisterDto.builder()
                .firstName("Richards")
                .lastName("Kohl")
                .email("richardsKohl@gmail.com")
                .password("indigo@violet")
                .gender(Gender.MALE)
                .build();

        when(registrationRepository.save(user)).thenReturn(user);
        assertEquals("Registration Successful", registrationService.registerNewUser(registerDto).getMessage());
    }

}