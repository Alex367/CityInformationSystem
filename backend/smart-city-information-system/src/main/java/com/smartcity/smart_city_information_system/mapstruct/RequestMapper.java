package com.smartcity.smart_city_information_system.mapstruct;

import com.smartcity.smart_city_information_system.dto.GetAllRequestsResponse;
import com.smartcity.smart_city_information_system.dto.PatchRequestRequest;
import com.smartcity.smart_city_information_system.entity.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(source = "request_members.user_id", target = "user_id")
    GetAllRequestsResponse toGetAllRequestsResponse(Request request);

    List<GetAllRequestsResponse> toGetAllRequestsResponseList(List<Request> requests);

    @Mapping(target = "id", ignore = true)
    void updateRequestFromPatchRequest(PatchRequestRequest dto, @MappingTarget Request request);
}
