package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.TypeDAO;
import com.smartcity.smart_city_information_system.dto.GetAllTypesResponse;
import com.smartcity.smart_city_information_system.dto.PatchTypeRequest;
import com.smartcity.smart_city_information_system.dto.PostTypeRequest;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.exception.AlreadyExistedEntityException;
import com.smartcity.smart_city_information_system.exception.DatabaseOperationException;
import com.smartcity.smart_city_information_system.exception.UnauthorizedException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    private TypeDAO typeDAO;

    @Autowired
    public TypeServiceImpl(TypeDAO typeDAO) {
        this.typeDAO = typeDAO;
    }

    @Override
    @Transactional
    public void addNewType(PostTypeRequest dto, Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("You can not add type.");
        }

        List<Type> allCreatedTypes = typeDAO.findAllTypes();
        boolean typeAlreadyExisted = allCreatedTypes.stream().anyMatch(
                t -> t.getType().equalsIgnoreCase(dto.getTypename())
        );
        if (typeAlreadyExisted) {
            throw new AlreadyExistedEntityException("Type already existed.");
        }

        Type theType = new Type(dto.getTypename(), "test.jpg", dto.getDescription());

        try {
            typeDAO.addNewType(theType);
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to save type "
                    + dto.getTypename()
                    + " due to db error", e);
        }
    }

    @Override
    public List<GetAllTypesResponse> findAllTypes() {
        List<GetAllTypesResponse> allTypes = typeDAO.findAllTypes()
                .stream().map(GetAllTypesResponse::from)
                .toList();
        return allTypes;
    }

    @Transactional
    @Override
    public String deleteType(String typeId, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("You can not delete a type.");
        }
        try {
            return typeDAO.deleteType(typeId);
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to delete a type due to db error", e);
        }
    }

    @Override
    public Type findById(String typeId) {
        return typeDAO.findById(typeId);
    }

    @Transactional
    @Override
    public Type patchType(PatchTypeRequest dto, Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("You can not patch type.");
        }

        Type type = typeDAO.findById(dto.getId());

        if (type.getType().equals(dto.getType())
                && type.getDescription().equals(dto.getDescription())
                && "test.jpg".equals(type.getPath_file())
        ) {
            throw new AlreadyExistedEntityException("No changes detected.");
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
