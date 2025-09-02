package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.CityDAO;
import com.smartcity.smart_city_information_system.dto.PatchCityRequest;
import com.smartcity.smart_city_information_system.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    private CityDAO cityDAO;

    @Autowired
    public CityServiceImpl(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    @Override
    public List<City> findAll() {
        return cityDAO.findAllCity();
    }

    @Transactional
    @Override
    public void addNew(City theCity) {
        cityDAO.addNewCity(theCity);
    }

    @Transactional
    @Override
    public String deleteCity(String id){
        return cityDAO.deleteCity(id);
    }

    @Override
    public City findById(String id) {
        return cityDAO.findById(id);
    }

    @Override
    public List<City> findAllByUserId(String userId) {
        return cityDAO.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public City patchCity(PatchCityRequest dto) {
        City city = cityDAO.findById(dto.getId());
        city.setCity(dto.getCity());
        city.setDescription(dto.getDescription());
        city.setPath_file("test.jpg");

        return city;
    }

}
