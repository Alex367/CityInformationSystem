package com.smartcity.smart_city_information_system.mapstruct;

import com.smartcity.smart_city_information_system.dto.GetAllTypesResponse;
import com.smartcity.smart_city_information_system.dto.PatchTypeRequest;
import com.smartcity.smart_city_information_system.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    GetAllTypesResponse toGetAllTypesResponse(Type type);

    List<GetAllTypesResponse> toGetAllTypesResponseList(List<Type> types);

    @Mapping(target = "id", ignore = true)
    void updateTypeFromPatchRequest(PatchTypeRequest dto, @MappingTarget Type type);

}
