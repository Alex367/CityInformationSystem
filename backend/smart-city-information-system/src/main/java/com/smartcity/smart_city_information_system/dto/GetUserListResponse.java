package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Roles;

public record GetUserListResponse(int id, String role, String user_id) {
        public static GetUserListResponse from(Roles role) {
            return new GetUserListResponse(
                    role.getId(), role.getRole(), role.getMember().getUser_id()
            );
        }
}

