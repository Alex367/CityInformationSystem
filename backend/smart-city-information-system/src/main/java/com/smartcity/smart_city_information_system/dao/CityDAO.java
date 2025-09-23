package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.City;

import java.util.List;

public interface CityDAO {
    List<City> findAllCity();
    void addNewCity(City theCity);
    String deleteCity(City theCity);
    City findById(String id);
    List<City> findAllByUserId(String userId);
    City findByCityName(String cityName);
    City findCityIdByUserCityName(String cityName, String userId);
}
