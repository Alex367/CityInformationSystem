package com.smartcity.smart_city_information_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponse {
    private String message;
    private String user_id;
}
