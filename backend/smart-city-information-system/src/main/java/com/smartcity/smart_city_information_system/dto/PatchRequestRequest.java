package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchRequestRequest {
    private int id;
    private String response;
    private String type;
    private String path_file;
    private String description;
    private Status status;
}
