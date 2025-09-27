package com.smartcity.smart_city_information_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostPlaceRequest {
    @NotBlank(message = "City can not be empty")
    private String city;
    @NotBlank(message = "Type can not be empty")
    private String type;
    @NotBlank(message = "Place can not be empty")
    @Size(min = 2, max = 45, message = "City must be between 2 and 45 characters")
    private String place;
    @NotBlank(message = "Path file can not be empty")
    private String path_file;
    @NotBlank(message = "Description can not be empty")
    private String description;
}
