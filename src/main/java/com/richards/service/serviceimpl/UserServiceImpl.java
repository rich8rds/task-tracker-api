package com.richards.service.serviceimpl;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.UserDto;
import com.richards.entity.User;
import com.richards.exceptions.UserNotFoundException;
import com.richards.repository.RegistrationRepository;
import com.richards.repository.UserRepository;
import com.richards.service.UserService;
import com.richards.session.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    @Override
    public ApiResponse<User> updateUser(UserDto userDto) {
        Long userId = UserInfo.getUserSessionId(session);
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("Username or password incorrect!"));

       if(user.getFirstName() != null && !user.getFirstName().isBlank())
           user.setFirstName(userDto.getFirstName());
       if(user.getLastName() != null && !user.getLastName().isBlank())
           user.setLastName(userDto.getLastName());
       if(user.getGender() != null && !user.getGender().toString().isBlank())
           user.setGender(userDto.getGender());

        return new ApiResponse<>("Account updated successfully", true, userRepository.save(user));
    }
}
