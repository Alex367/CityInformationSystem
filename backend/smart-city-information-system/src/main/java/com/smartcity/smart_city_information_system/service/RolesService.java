package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.entity.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> findAllUsers(String adminId);
    String deleteUser(String userId);
}
