package com.richards.service.serviceimpl;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.LoginDto;
import com.richards.entity.User;
import com.richards.exceptions.UserNotFoundException;
import com.richards.repository.LoginRepository;
import com.richards.service.LoginService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;
    @Override
    public ApiResponse<User> login(LoginDto loginDto) {
        if(loginDto == null) throw new UserNotFoundException("Login Details Cannot Be Empty or Null.");

        Optional<User> userOptional = loginRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        userOptional.orElseThrow(() -> new UserNotFoundException("Username or password incorrect!"));
        return new ApiResponse<>("Login successful", true, userOptional.get());
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOptional = loginRepository.findByEmail(email);
//        User user = userOptional.orElseThrow(() -> new UserNotFoundException("Incorrect email or password!"));
//        new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
//        return null;
//    }
}
