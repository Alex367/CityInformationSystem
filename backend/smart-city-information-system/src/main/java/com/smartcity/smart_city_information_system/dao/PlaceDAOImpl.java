package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Place;
import com.smartcity.smart_city_information_system.entity.Type;
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

    @Override
    public List<Place> findAllPlacesByUserId(String userId) {
        TypedQuery<Place> allPlaces = entityManager.createQuery(
                "select p from Place p join p.theCity c where c.members.user_id = :userId", Place.class
        ).setParameter("userId", userId);

        return allPlaces.getResultList();
    }

    @Override
    public String deletePlace(String placeId) {
        Place thePlace = entityManager.find(Place.class, placeId);
        String placeName = thePlace.getPlace();
        entityManager.remove(thePlace);
        return placeName;
    }
}
