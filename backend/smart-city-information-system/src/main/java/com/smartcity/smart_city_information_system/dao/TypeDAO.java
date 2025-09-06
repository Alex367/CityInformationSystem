package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Type;

import java.util.List;

public interface TypeDAO {
    void addNewType(Type theType);
    List<Type> findAllTypes();
    String deleteType(String typeId);
    Type findById(String typeId);
}
