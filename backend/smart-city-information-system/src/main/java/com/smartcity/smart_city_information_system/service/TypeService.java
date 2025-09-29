package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.GetAllTypesResponse;
import com.smartcity.smart_city_information_system.dto.PatchTypeRequest;
import com.smartcity.smart_city_information_system.dto.PostTypeRequest;
import com.smartcity.smart_city_information_system.entity.Type;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface TypeService {
    void addNewType(PostTypeRequest dto, Authentication auth);
    List<GetAllTypesResponse> findAllTypes();
    String deleteType(String typeId, Authentication auth);
    Type findById(String typeId);
    Type patchType(PatchTypeRequest dto, Authentication auth);
    Type findByTypeName(String typeName);
}
