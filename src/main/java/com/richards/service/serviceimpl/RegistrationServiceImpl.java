package com.richards.service.serviceimpl;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.RegisterDto;
import com.richards.entity.User;
import com.richards.enums.ValidationResult;
import com.richards.repository.RegistrationRepository;
import com.richards.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.richards.validator.RegistrationValidationService.*;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final RegistrationRepository registrationRepository;
    @Override
    public ApiResponse<User> registerNewUser(RegisterDto registerDto) {
        ValidationResult result = isFirstNameValid().and(isSurnameValid())
                .and(isEmailValid()).and(emailExists(registrationRepository))
                .and(isPasswordValid()).and(isGenderValid()).apply(registerDto);

        if(result.equals(ValidationResult.SUCCESS)) {
            User user = User.builder()
                    .firstName(registerDto.getFirstName())
                    .lastName(registerDto.getLastName())
                    .email(registerDto.getEmail())
                    .password(registerDto.getPassword())
                    .gender(registerDto.getGender())
                    .build();
            return new ApiResponse<>("Registration Successful", true, registrationRepository.save(user));

        }return new ApiResponse<>(result.toString(), false, null);
    }
}
