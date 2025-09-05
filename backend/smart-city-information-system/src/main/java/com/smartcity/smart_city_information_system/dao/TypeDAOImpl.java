package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TypeDAOImpl implements TypeDAO{

    private EntityManager entityManager;

    @Autowired
    public TypeDAOImpl(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public void addNewType(Type theType) {
        entityManager.persist(theType);
    }

    @Override
    public List<Type> findAllTypes() {
        TypedQuery<Type> findAllTypes = entityManager.createQuery("from Type", Type.class);
        return findAllTypes.getResultList();
    }

    @Override
    public String deleteType(String typeId) {
        Type foundType = entityManager.find(Type.class, typeId);
        entityManager.remove(foundType);
        return foundType.getType();
    }
}
