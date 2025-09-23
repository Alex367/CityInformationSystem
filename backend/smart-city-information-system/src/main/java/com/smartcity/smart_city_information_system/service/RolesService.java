package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.UserListResponse;
import com.smartcity.smart_city_information_system.entity.Roles;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RolesService {
    List<UserListResponse> findAllUsers(Authentication auth);
    String deleteUser(String userId, Authentication auth);
}
