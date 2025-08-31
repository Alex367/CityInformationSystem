package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.City;

import java.util.List;

public interface CityDAO {
    List<City> findAllCity();

    void addNewCity(City theCity);

    String deleteCity(String id);

    City findById(String id);
}
