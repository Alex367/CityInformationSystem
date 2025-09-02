package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.MembersDAO;
import com.smartcity.smart_city_information_system.entity.Members;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembersServiceImpl implements MembersService{

    private MembersDAO membersDAO;

    @Autowired
    public MembersServiceImpl(MembersDAO membersDAO) {
        this.membersDAO = membersDAO;
    }

    @Override
    public Members findById(String id) {
        return membersDAO.findById(id);
    }
}
