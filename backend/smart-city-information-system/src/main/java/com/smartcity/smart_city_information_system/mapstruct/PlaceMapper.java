package com.smartcity.smart_city_information_system.mapstruct;

import com.smartcity.smart_city_information_system.dto.GetAllPlacesResponse;
import com.smartcity.smart_city_information_system.entity.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    @Mapping(source = "theCity.city", target = "city")
    @Mapping(source = "theType.type", target = "type")
    GetAllPlacesResponse toGetAllPlacesResponse(Place place);

    List<GetAllPlacesResponse> toGetAllPlacesResponseList(List<Place> places);

}
