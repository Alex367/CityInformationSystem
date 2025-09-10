package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Request;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestDAOImpl implements RequestDAO{

    private EntityManager entityManager;

    @Autowired
    public RequestDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String addNewRequest(Request theRequest) {
        String typenameRequest = theRequest.getType();
        entityManager.persist(theRequest);
        return typenameRequest;
    }

    @Override
    public List<Request> findAllRequests() {
        TypedQuery<Request> theRequests = entityManager.createQuery("FROM Request", Request.class);
        return theRequests.getResultList();
    }
}
