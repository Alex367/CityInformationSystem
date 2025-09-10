package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Request;
import com.smartcity.smart_city_information_system.enums.Status;

public record GetAllRequestsResponse (int id,
                                      String request,
                                      String response,
                                      String type,
                                      String path_file,
                                      String description,
                                      Status status,
                                      String user_id) {

    public static GetAllRequestsResponse from(Request request){
        return new GetAllRequestsResponse(
                request.getId(),
                request.getRequest(),
                request.getResponse(),
                request.getType(),
                request.getPath_file(),
                request.getDescription(),
                request.getStatus(),
                request.getRequest_members().getUser_id()
        );
    }
}
