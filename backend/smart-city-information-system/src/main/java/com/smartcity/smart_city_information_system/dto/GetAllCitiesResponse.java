package com.smartcity.smart_city_information_system.dto;

public record GetAllCitiesResponse(int id,
                                   String city,
                                   String path_file,
                                   String description,
                                   String userId) {
}

