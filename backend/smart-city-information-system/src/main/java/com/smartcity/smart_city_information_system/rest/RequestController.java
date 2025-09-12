package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.entity.Request;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.enums.Status;
import com.smartcity.smart_city_information_system.service.MembersService;
import com.smartcity.smart_city_information_system.service.RequestService;
import com.smartcity.smart_city_information_system.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RequestController {

    private RequestService requestService;
    private MembersService membersService;
    private TypeService typeService;

    @Autowired
    public RequestController(RequestService requestService, MembersService membersService, TypeService typeService) {
        this.requestService = requestService;
        this.membersService = membersService;
        this.typeService = typeService;
    }

    @PostMapping("/request")
    public ResponseEntity<PostRequestResponse> addNewRequest(@RequestBody PostRequestRequest dto, Authentication auth){
        System.out.println(dto);

        if(dto.getType() == null){
            return ResponseEntity.badRequest().body(new PostRequestResponse("Type name cannot be empty"));
        }

        List<Type> allCreatedTypes = typeService.findAllTypes();
        boolean typeAlreadyExisted = allCreatedTypes.stream().anyMatch(
                t -> t.getType().equalsIgnoreCase(dto.getType())
        );
        if(typeAlreadyExisted){
            return ResponseEntity.badRequest().body(new PostRequestResponse("Type already exists"));
        }

        Request req = new Request(
                dto.getRequest(),
                null,
                dto.getType(),
                "test.jpg",
                dto.getDescription(),
                Status.PENDING
        );
        Members member = membersService.findById(auth.getName());
        req.setRequest_members(member);
        requestService.addNewRequest(req);
        return ResponseEntity.ok(new PostRequestResponse("request is created!"));
    }

    @GetMapping("/requestList")
    public Map<String, List<GetAllRequestsResponse>> getAllResponse(Authentication auth){

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<GetAllRequestsResponse> theRequests = new ArrayList<>();
        if(!isAdmin){
            theRequests = requestService.findAllRequestsByUser(auth.getName())
                .stream().map(GetAllRequestsResponse::from).toList();
        }else{
            theRequests = requestService.findAllRequests()
                    .stream().map(GetAllRequestsResponse::from).toList();
        }
        return Map.of("requestList", theRequests);
    }

    @PatchMapping("/request")
    public ResponseEntity<PatchRequestResponse> patchResponse(@RequestBody PatchRequestRequest dto){

        if(dto.getStatus().equals(Status.PENDING)){
            return ResponseEntity.badRequest()
                    .body(new PatchRequestResponse("Values can not be PENDING", dto.getStatus()));
        }

        Request patchedRequest = requestService.patchRequest(dto);
        if(dto.getStatus().equals(Status.ACCEPTED)){
            Type theType = new Type(dto.getType(), "test.jpg", dto.getDescription());
            typeService.addNewType(theType);
        }
        return ResponseEntity.ok(new PatchRequestResponse("responded", dto.getStatus()));
    }

    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<DeleteRequestResponse> deleteRequest(@PathVariable String requestId){
        requestService.deleteRequestById(requestId);
        return ResponseEntity.ok(new DeleteRequestResponse(requestId));
    }

}
