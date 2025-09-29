package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.GetAllCitiesResponse;
import com.smartcity.smart_city_information_system.dto.PatchCityRequest;
import com.smartcity.smart_city_information_system.dto.PostCityRequest;
import com.smartcity.smart_city_information_system.entity.City;

import java.util.List;

public interface CityService {
    List<City> findAll();
    void addNewCity(PostCityRequest dto, String userId);
    String deleteCity(String cityId, String userId);
    City findById(String id);
    List<GetAllCitiesResponse> findAllByUserId(String userId);
    City patchCity(PatchCityRequest dto);
    City findByCityName(String cityName);
    City findCityIdByUserCityName(String cityName, String userId);
}
