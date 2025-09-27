package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.RolesDAO;
import com.smartcity.smart_city_information_system.dto.GetUserListResponse;
import com.smartcity.smart_city_information_system.entity.Roles;
import com.smartcity.smart_city_information_system.exception.DatabaseOperationException;
import com.smartcity.smart_city_information_system.exception.UnauthorizedException;
import com.smartcity.smart_city_information_system.mapstruct.UserMapper;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    private RolesDAO rolesDAO;
    private UserMapper userMapper;

    @Autowired
    public RolesServiceImpl(RolesDAO rolesDAO, UserMapper userMapper) {
        this.rolesDAO = rolesDAO;
        this.userMapper = userMapper;
    }

    @Override
    public List<GetUserListResponse> findAllUsers(Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("You can not get user list.");
        }

        List<Roles> allUsers = rolesDAO.findAllUsers(auth.getName());
        return userMapper.toGetUserListResponseList(allUsers);
    }

    @Override
    @Transactional
    public String deleteUser(String userId, Authentication auth) {

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            throw new UnauthorizedException("You can not get user list.");
        }

        try {
            return rolesDAO.deleteUser(userId);
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to delete a user due to db error", e);
        }
    }

}
