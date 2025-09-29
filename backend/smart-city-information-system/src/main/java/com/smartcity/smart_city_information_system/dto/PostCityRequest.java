package com.smartcity.smart_city_information_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCityRequest {
    @NotBlank(message = "City can not be null or empty")
    @Size(min = 2, max = 45, message = "City must be between 2 and 45 characters")
    @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "City must contain only letters, spaces, hyphens, or apostrophes")
    private String city;

    @NotBlank(message = "Path file cannot be null or empty")
    @Size(min = 1, max = 45, message = "Path file must be between 1 and 45 characters")
    private String path_file;

    @NotBlank(message = "Description can not be null or empty")
    @Size(min = 1, max = 45, message = "Description must be between 2 and 45 characters")
    private String description;
}
