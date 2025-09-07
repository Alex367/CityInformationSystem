package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Place;

import java.util.List;

public interface PlaceDAO {
    List<Place> findAllPlaces();
    void addNewPlace(Place thePlace);
}
