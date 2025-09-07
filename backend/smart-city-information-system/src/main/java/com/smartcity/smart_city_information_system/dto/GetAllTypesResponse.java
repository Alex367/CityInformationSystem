package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Type;

public record GetAllTypesResponse(int id, String type, String path_file, String description) {
    public static GetAllTypesResponse from(Type type){
        return new GetAllTypesResponse(
                type.getId(), type.getType(), type.getPath_file(), type.getDescription()
        );
    }
}
