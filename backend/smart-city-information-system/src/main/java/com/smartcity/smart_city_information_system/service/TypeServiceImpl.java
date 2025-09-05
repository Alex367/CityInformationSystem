package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.TypeDAO;
import com.smartcity.smart_city_information_system.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    private TypeDAO typeDAO;

    @Autowired
    public TypeServiceImpl(TypeDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    @Override
    @Transactional
    public void addNewType(Type theType) {
        typeDAO.addNewType(theType);
    }

    @Override
    public List<Type> findAllTypes() {
        return typeDAO.findAllTypes();
    }

    @Override
    @Transactional
    public String deleteType(String typeId) {
        return typeDAO.deleteType(typeId);
    }
}
