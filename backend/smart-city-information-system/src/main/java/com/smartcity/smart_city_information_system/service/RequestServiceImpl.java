package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.RequestDAO;
import com.smartcity.smart_city_information_system.dao.TypeDAO;
import com.smartcity.smart_city_information_system.dto.GetAllRequestsResponse;
import com.smartcity.smart_city_information_system.dto.PatchRequestRequest;
import com.smartcity.smart_city_information_system.dto.PostRequestRequest;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.entity.Request;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.enums.Status;
import com.smartcity.smart_city_information_system.exception.*;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    private RequestDAO requestDAO;
    private TypeDAO typeDAO;
    private MembersService membersService;

    @Autowired
    public RequestServiceImpl(RequestDAO requestDAO,
                              TypeDAO typeDAO,
                              MembersService membersService) {
        this.requestDAO = requestDAO;
        this.typeDAO = typeDAO;
        this.membersService = membersService;
    }

    @Transactional
    @Override
    public void addNewRequest(PostRequestRequest dto, String userId) {
        List<Type> allCreatedTypes = typeDAO.findAllTypes();
        boolean typeAlreadyExisted = allCreatedTypes.stream().anyMatch(
                t -> t.getType().equalsIgnoreCase(dto.getType())
        );
        if (typeAlreadyExisted) {
            throw new AlreadyExistedEntityException("Type already existed");
        }

        Request req = new Request(
                dto.getRequest(),
                null,
                dto.getType(),
                "test.jpg",
                dto.getDescription(),
                Status.PENDING
        );
        Members member = membersService.findById(userId);
        req.setRequest_members(member);

        try {
            requestDAO.addNewRequest(req);
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to add request due to db error", e);
        }
    }

    @Override
    public List<GetAllRequestsResponse> findAllRequests(Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<GetAllRequestsResponse> theRequests;
        try {
            if (!isAdmin) {
                theRequests = requestDAO.findAllRequestsByUser(auth.getName())
                        .stream().map(GetAllRequestsResponse::from).toList();
            } else {
                theRequests = requestDAO.findAllRequests()
                        .stream().map(GetAllRequestsResponse::from).toList();
            }
            return theRequests;
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to find requests due to db error", e);
        }
    }

    @Override
    public Request findById(int requestId) {
        return requestDAO.findById(requestId);
    }

    @Transactional
    @Override
    public void patchRequest(PatchRequestRequest dto) {

        if (dto.getStatus().equals(Status.PENDING)) {
            throw new InvalidInputException("Values can not be PENDING");
        }
        Request theRequest = requestDAO.findById(dto.getId());
        if(theRequest == null){
            throw new NotFoundException("request with the id " + dto.getId() + " is not found");
        }

        theRequest.setResponse(dto.getResponse());
        theRequest.setType(dto.getType());
        theRequest.setPath_file(dto.getPath_file());
        theRequest.setDescription(dto.getDescription());
        theRequest.setStatus(dto.getStatus());

        if (dto.getStatus().equals(Status.ACCEPTED)) {
            Type theType = new Type(dto.getType(), "test.jpg", dto.getDescription());
            try {
                typeDAO.addNewType(theType);
            } catch (PersistenceException e) {
                throw new DatabaseOperationException("Failed to add new type due to db error", e);
            }
        }
    }

    @Transactional
    @Override
    public void deleteRequestById(String requestId, Authentication auth) {
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(!isAdmin){
            throw new UnauthorizedException("You can not delete requests");
        }
        try {
            requestDAO.deleteRequestById(requestId);
        } catch (PersistenceException e){
            throw new DatabaseOperationException("Failed to delete a request due to db error", e);
        }
    }
}
