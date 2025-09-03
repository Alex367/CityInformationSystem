package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public record UserListResponse(int id, String role, String user_id) {
        public static UserListResponse from(Roles role) {
            return new UserListResponse(
                    role.getId(), role.getRole(), role.getMember().getUser_id()
            );
        }
}

