package com.richards.controller;

import com.richards.dto.UserDto;
import com.richards.entity.User;
import com.richards.enums.Gender;
import com.richards.repository.LoginRepository;
import com.richards.repository.UserRepository;
import com.richards.service.LoginService;
import com.richards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@RequiredArgsConstructor
@SpringBootTest
class UserControllerTest {
    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

    @MockBean
    LoginRepository loginRepository;

    @MockBean
    UserRepository userRepository;

    private User user;

    @Autowired
    private HttpSession session;

    @BeforeEach
    public void setUp() throws Exception {
        user = User.builder()
                .id(2L)
                .firstName("Richards")
                .lastName("Benson")
                .email("richardsBenson@gmail.com")
                .password("bensonMan")
                .build();

        session.setAttribute("userDetails", user);
    }

    @Test
    @DisplayName("Should search for user's id and check if equal to testId")
    void findUserById() {
        Long userId = user.getId();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertEquals(2L, userRepository.findById(userId).orElse(new User()).getId());

    }

    @Test
    void updateUserDetails() {
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDto userDto = UserDto.builder()
                .firstName("Orlando")
                .lastName("Bloom")
                .gender(Gender.OTHER)
                .build();

        User updatedUser = User.builder()
                .id(2L)
                .firstName("Orlando")
                .lastName("Bloom")
                .email("richardsBenson@gmail.com")
                .password("bensonMan")
                .gender(Gender.OTHER)
                .build();

        when(userRepository.save(user)).thenReturn(updatedUser);
        assertEquals(updatedUser, userService.updateUser(userDto).getData());
    }
}