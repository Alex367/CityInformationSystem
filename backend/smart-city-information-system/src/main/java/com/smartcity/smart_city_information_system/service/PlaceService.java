package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.entity.Place;

import java.util.List;

public interface PlaceService {

    List<Place> findAllPlaces();
    void addNewPlace(Place thePlace);
    List<Place> findAllPlacesByUserId(String userId);
    String deletePlace(String placeId);
}
