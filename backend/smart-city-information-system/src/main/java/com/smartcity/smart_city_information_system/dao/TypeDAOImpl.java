package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    @Override
    public Type findById(String typeId) {
        return entityManager.find(Type.class, typeId);
    }

    @Override
    public Type findByTypeName(String typeName) {
        try {
            return entityManager.createQuery("select t from Type t where t.type = :typeName", Type.class
            ).setParameter("typeName", typeName).getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
