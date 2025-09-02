package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Members;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MembersDAOImpl implements MembersDAO{

    private EntityManager entityManager;

    @Autowired
    public MembersDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Members findById(String id) {
        return entityManager.find(Members.class, id);
    }
}
