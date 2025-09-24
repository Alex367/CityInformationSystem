package com.smartcity.smart_city_information_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostRegistrationRequest {
    @NotBlank(message = "User id can not be null or empty")
    @Size(min = 1, max = 50, message = "User id must be between 1 and 50 characters")
    private String user_id;

    @NotBlank(message = "Password can not be null or empty")
    @Size(min = 3, max = 68, message = "Password must be between 3 and 68 characters")
    private String pw;
    private String active;
}
