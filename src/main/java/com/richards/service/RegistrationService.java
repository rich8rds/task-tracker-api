package com.richards.service;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.RegisterDto;
import com.richards.entity.User;

public interface RegistrationService {

    ApiResponse<User> registerNewUser(RegisterDto registerDto);
}
