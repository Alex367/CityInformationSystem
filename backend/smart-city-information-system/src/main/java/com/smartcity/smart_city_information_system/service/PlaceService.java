package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.GetAllPlacesResponse;
import com.smartcity.smart_city_information_system.dto.PostPlaceRequest;
import com.smartcity.smart_city_information_system.entity.Place;

import java.util.List;

public interface PlaceService {

    List<Place> findAllPlaces();
    void addNewPlace(PostPlaceRequest dto, String userId);
    List<GetAllPlacesResponse> findAllPlacesByUserId(String userId);
    String deletePlace(String placeId, String userId);
    Place findPlaceById(String placeId);
}
