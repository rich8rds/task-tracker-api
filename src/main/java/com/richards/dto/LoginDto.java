package com.richards.dto;

import com.richards.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    private String email;
    private String password;
}
