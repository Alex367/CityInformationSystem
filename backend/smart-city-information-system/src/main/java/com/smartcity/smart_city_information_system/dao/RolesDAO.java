package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Roles;

import java.util.List;

public interface RolesDAO {
    List<Roles> findAllUsers(String adminId);
    String deleteUser(String userId);
}
