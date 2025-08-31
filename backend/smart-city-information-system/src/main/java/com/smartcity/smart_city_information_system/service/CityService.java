package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.entity.City;

import java.util.List;

public interface CityService {
    List<City> findAll();
    void addNew(City theCity);
    String deleteCity(String id);
    City findById(String id);
}
