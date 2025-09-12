package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.PatchRequestRequest;
import com.smartcity.smart_city_information_system.entity.Request;

import java.util.List;

public interface RequestService {
    String addNewRequest(Request theRequest);
    List<Request> findAllRequests();
    Request findById(int requestId);
    Request patchRequest(PatchRequestRequest dto);
    List<Request> findAllRequestsByUser(String userId);
    void deleteRequestById(String requestId);
}
