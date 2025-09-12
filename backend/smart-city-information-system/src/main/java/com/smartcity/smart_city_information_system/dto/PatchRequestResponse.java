package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatchRequestResponse {
    private String message;
    private Status status;
}
