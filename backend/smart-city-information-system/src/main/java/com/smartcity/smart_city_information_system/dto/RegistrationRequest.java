package com.smartcity.smart_city_information_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistrationRequest {
    private String user_id;
    private String pw;
    private String active;
}
