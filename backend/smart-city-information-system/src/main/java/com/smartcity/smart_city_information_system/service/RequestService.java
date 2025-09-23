package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dto.GetAllRequestsResponse;
import com.smartcity.smart_city_information_system.dto.PatchRequestRequest;
import com.smartcity.smart_city_information_system.dto.PostRequestRequest;
import com.smartcity.smart_city_information_system.entity.Request;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RequestService {
    void addNewRequest(PostRequestRequest dto, String userId);
    List<GetAllRequestsResponse> findAllRequests(Authentication auth);
    Request findById(int requestId);
    void patchRequest(PatchRequestRequest dto);
    void deleteRequestById(String requestId, Authentication auth);
}
