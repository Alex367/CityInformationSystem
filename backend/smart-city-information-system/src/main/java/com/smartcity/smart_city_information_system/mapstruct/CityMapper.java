package com.smartcity.smart_city_information_system.mapstruct;

import com.smartcity.smart_city_information_system.dto.GetAllCitiesResponse;
import com.smartcity.smart_city_information_system.dto.PatchCityRequest;
import com.smartcity.smart_city_information_system.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(source = "members.user_id", target = "userId")
    GetAllCitiesResponse toGetAllCitiesResponse(City city);

    List<GetAllCitiesResponse> toGetAllCitiesResponseList(List<City> cities);

    @Mapping(target = "id", ignore = true)
    void updateCityFromPatchRequest(PatchCityRequest dto, @MappingTarget City city);

}
