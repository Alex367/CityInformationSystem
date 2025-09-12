package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.PatchCityRequest;
import com.smartcity.smart_city_information_system.entity.City;

import java.util.List;

public interface CityService {
    List<City> findAll();
    void addNew(City theCity);
    String deleteCity(String id);
    City findById(String id);
    List<City> findAllByUserId(String userId);
    City patchCity(PatchCityRequest dto);
    City findByCityName(String cityName);
    City findByCityNameUserId(String userId, String cityName);
}
