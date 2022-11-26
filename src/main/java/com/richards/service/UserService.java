package com.richards.service;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.UserDto;
import com.richards.entity.User;

public interface UserService {
    ApiResponse<User> updateUser(UserDto userDto);
}
