package com.smartcity.smart_city_information_system.dto;

import com.smartcity.smart_city_information_system.enums.Status;

public record GetAllRequestsResponse (int id,
                                      String request,
                                      String response,
                                      String type,
                                      String path_file,
                                      String description,
                                      Status status,
                                      String user_id) {

}
