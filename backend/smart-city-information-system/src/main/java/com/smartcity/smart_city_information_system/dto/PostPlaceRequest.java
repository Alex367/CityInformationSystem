package com.smartcity.smart_city_information_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostPlaceRequest {
    private String city;
    private String type;
    private String place;
    private String path_file;
    private String description;
}
