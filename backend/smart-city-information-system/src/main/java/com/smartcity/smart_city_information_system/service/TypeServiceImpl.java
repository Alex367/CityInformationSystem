package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.TypeDAO;
import com.smartcity.smart_city_information_system.dto.PatchTypeRequest;
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

    @Transactional
    @Override
    public String deleteType(String typeId) {
        return typeDAO.deleteType(typeId);
    }

    @Override
    public Type findById(String typeId) {
        return typeDAO.findById(typeId);
    }

    @Transactional
    @Override
    public Type patchType(PatchTypeRequest dto) {
        Type type = typeDAO.findById(dto.getId());

        if(type.getType().equals(dto.getType())
                && type.getDescription().equals(dto.getDescription())
                && "test.jpg".equals(type.getPath_file())
        ) {
            throw new IllegalStateException("No changes detected");
        }

        type.setType(dto.getType());
        type.setDescription(dto.getDescription());
        type.setPath_file("test.jpg");

        return type;
    }

    @Override
    public Type findByTypeName(String typeName) {
        return typeDAO.findByTypeName(typeName);
    }
}
