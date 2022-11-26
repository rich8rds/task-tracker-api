package com.richards.service;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.LoginDto;
import com.richards.entity.User;

public interface LoginService {
    ApiResponse<User> login(LoginDto loginDto);
}
