package com.richards.controller;

import com.richards.apiresponse.ApiResponse;
import com.richards.apiresponse.ErrorResponse;
import com.richards.dto.LoginDto;
import com.richards.dto.RegisterDto;
import com.richards.entity.User;
import com.richards.exceptions.TaskServiceException;
import com.richards.service.LoginService;
import com.richards.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final LoginService loginService;

    @PostMapping("/register")
    public ApiResponse<User> userRegistration(@RequestBody RegisterDto registerDto) {
        return registrationService.registerNewUser(registerDto);
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "Welcome to the login page";
    }

    @PostMapping("/login")
    public ApiResponse<User> login(@RequestBody LoginDto loginDto, HttpSession session) {
        ApiResponse<User> apiResponse = loginService.login(loginDto);
        if(apiResponse.getStatus())
            session.setAttribute("userDetails", apiResponse.getData());
        return loginService.login(loginDto);
    }

//    @RequestMapping("/*")
//    public void pageNotFoundRoutes() {
//        throw new TaskServiceException("Page Not Found");
//    }
}
