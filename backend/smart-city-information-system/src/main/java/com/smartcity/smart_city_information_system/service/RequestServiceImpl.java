package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.RequestDAO;
import com.smartcity.smart_city_information_system.dto.PatchRequestRequest;
import com.smartcity.smart_city_information_system.entity.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService{

    private RequestDAO requestDAO;

    @Autowired
    public RequestServiceImpl(RequestDAO requestDAO) {
        this.requestDAO = requestDAO;
    }

    @Transactional
    @Override
    public String addNewRequest(Request theRequest) {
        return requestDAO.addNewRequest(theRequest);
    }

    @Override
    public List<Request> findAllRequests() {
        return requestDAO.findAllRequests();
    }

    @Override
    public Request findById(int requestId) {
        return requestDAO.findById(requestId);
    }

    @Transactional
    @Override
    public Request patchRequest(PatchRequestRequest dto) {
        Request theRequest = requestDAO.findById(dto.getId());

        theRequest.setResponse(dto.getResponse());
        theRequest.setType(dto.getType());
        theRequest.setPath_file(dto.getPath_file());
        theRequest.setDescription(dto.getDescription());
        theRequest.setStatus(dto.getStatus());

        return theRequest;
    }

    @Override
    public List<Request> findAllRequestsByUser(String userId) {
        return requestDAO.findAllRequestsByUser(userId);
    }

    @Transactional
    @Override
    public void deleteRequestById(String requestId) {
        requestDAO.deleteRequestById(requestId);
    }
}
