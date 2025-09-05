package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetAllTypesResponse {

    private List<Type> message;

}
