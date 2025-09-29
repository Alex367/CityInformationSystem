package com.smartcity.smart_city_information_system.mapstruct;

import com.smartcity.smart_city_information_system.dto.GetUserListResponse;
import com.smartcity.smart_city_information_system.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "member.user_id", target = "user_id")
    GetUserListResponse toGetUserListResponse(Roles role);

    List<GetUserListResponse> toGetUserListResponseList(List<Roles> roles);

}
