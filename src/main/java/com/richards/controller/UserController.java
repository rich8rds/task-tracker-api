package com.richards.controller;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.LoginDto;
import com.richards.dto.UserDto;
import com.richards.entity.User;
import com.richards.service.LoginService;
import com.richards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update")
    public ApiResponse<User> updateUserDetails(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
}
