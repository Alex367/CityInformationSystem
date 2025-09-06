package com.smartcity.smart_city_information_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatchTypeRequest {
    private String id;
    private String type;
    private String path_file;
    private String description;
}
