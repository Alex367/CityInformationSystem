package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.RequestDAO;
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
}
