package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.City;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDAOImpl implements CityDAO{

    private EntityManager entityManager;

    @Autowired
    public CityDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<City> findAllCity() {

        TypedQuery<City> theQuery = entityManager.createQuery(
                "from City", City.class
        );

        return theQuery.getResultList();
    }

    @Override
    public void addNewCity(City theCity) {
        entityManager.persist(theCity);
    }

    @Override
    public String deleteCity(City theCity) {
        String cityName = theCity.getCity();
        entityManager.remove(theCity);
        return cityName;
    }

    @Override
    public City findById(String id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public List<City> findAllByUserId(String userId) {
        return entityManager.createQuery(
                "select c from City c where c.members.user_id = :userId", City.class
        ).setParameter("userId", userId).getResultList();
    }

    @Override
    public City findByCityName(String cityName) {
        return entityManager.createQuery(
                "select c from City c where c.city = :cityName", City.class
        ).setParameter("cityName", cityName).getSingleResult();
    }

    @Override
    public City findByCityNameUserId(String userId, String cityName) {
        TypedQuery<City> theCity = entityManager.createQuery(
                "select c from City c where c.city = :cityName and c.members.user_id = :userId", City.class
        );
        theCity.setParameter("cityName", cityName);
        theCity.setParameter("userId", userId);

        return theCity.getSingleResult();
    }

}
