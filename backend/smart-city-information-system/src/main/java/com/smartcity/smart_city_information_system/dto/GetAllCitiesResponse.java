package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.City;

public record GetAllCitiesResponse(int id, String city, String path_file, String description, String userId) {
    public static GetAllCitiesResponse from(City city) {
        return new GetAllCitiesResponse(
            city.getId(),
            city.getCity(),
            city.getPath_file(),
            city.getDescription(),
            city.getMembers().getUser_id()
        );
    }
}

