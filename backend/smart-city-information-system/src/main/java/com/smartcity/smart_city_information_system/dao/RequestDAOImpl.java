package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Request;
import com.smartcity.smart_city_information_system.exception.NotFoundException;
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
    public void addNewRequest(Request theRequest) {
        entityManager.persist(theRequest);
    }

    @Override
    public List<Request> findAllRequests() {
        TypedQuery<Request> theRequests = entityManager.createQuery("FROM Request", Request.class);
        return theRequests.getResultList();
    }

    @Override
    public Request findById(int requestId) {
        return entityManager.find(Request.class, requestId);
    }

    @Override
    public List<Request> findAllRequestsByUser(String userId) {
        TypedQuery<Request> theQuery = entityManager
                .createQuery("select r from Request r where r.request_members.user_id = :userId", Request.class)
                .setParameter("userId", userId);
        return theQuery.getResultList();
    }

    @Override
    public void deleteRequestById(String requestId) {
        Request theRequest = entityManager.find(Request.class, requestId);
        if(theRequest == null){
            throw new NotFoundException("request is not exist");
        }
        entityManager.remove(theRequest);
    }
}
