package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.entity.Type;

import java.util.List;

public interface TypeService {
    void addNewType(Type theType);
    List<Type> findAllTypes();
    String deleteType(String typeId);
}
