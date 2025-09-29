package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.GetUserListResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RolesService {
    List<GetUserListResponse> findAllUsers(Authentication auth);
    String deleteUser(String userId, Authentication auth);
}
