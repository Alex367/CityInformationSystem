package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Place;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlaceDAOImpl implements PlaceDAO{

    private EntityManager entityManager;

    @Autowired
    public PlaceDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Place> findAllPlaces() {
        TypedQuery<Place> allPlaces = entityManager.createQuery("FROM Place", Place.class);
        return allPlaces.getResultList();
    }

    @Override
    public void addNewPlace(Place thePlace) {
        entityManager.persist(thePlace);
    }
}
