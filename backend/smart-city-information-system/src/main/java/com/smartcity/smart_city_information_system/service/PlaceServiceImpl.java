package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.PlaceDAO;
import com.smartcity.smart_city_information_system.entity.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService{
    private PlaceDAO placeDAO;

    @Autowired
    public PlaceServiceImpl(PlaceDAO placeDAO) {
        this.placeDAO = placeDAO;
    }

    @Override
    public List<Place> findAllPlaces() {
        return placeDAO.findAllPlaces();
    }

    @Transactional
    @Override
    public void addNewPlace(Place thePlace) {
        placeDAO.addNewPlace(thePlace);
    }
}
