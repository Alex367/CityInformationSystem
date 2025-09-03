package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.RolesDAO;
import com.smartcity.smart_city_information_system.entity.Roles;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService{

    private RolesDAO rolesDAO;

    @Autowired
    public RolesServiceImpl(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    @Override
    public List<Roles> findAllUsers(String adminId) {
        return rolesDAO.findAllUsers(adminId);
    }

    @Override
    @Transactional
    public String deleteUser(String userId) {
        return rolesDAO.deleteUser(userId);
    }

}
