package com.smartcity.smart_city_information_system.dao;

import com.smartcity.smart_city_information_system.entity.Request;

import java.util.List;

public interface RequestDAO {
    String addNewRequest(Request theRequest);
    List<Request> findAllRequests();
}
