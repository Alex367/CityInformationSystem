package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Place;

public record GetAllPlacesResponse(int id,
                                   String place,
                                   String path_file,
                                   String description,
                                   String city,
                                   String type) {
    public static GetAllPlacesResponse from(Place place){
        return new GetAllPlacesResponse(
                place.getId(),
                place.getPlace(),
                place.getPath_file(),
                place.getDescription(),
                place.getTheCity().getCity(),
                place.getTheType().getType()
        );
    }
}
