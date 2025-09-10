package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.GetAllRequestsResponse;
import com.smartcity.smart_city_information_system.dto.PostRequestRequest;
import com.smartcity.smart_city_information_system.dto.PostRequestResponse;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.entity.Request;
import com.smartcity.smart_city_information_system.enums.Status;
import com.smartcity.smart_city_information_system.service.MembersService;
import com.smartcity.smart_city_information_system.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RequestController {

    private RequestService requestService;
    private MembersService membersService;

    @Autowired
    public RequestController(RequestService requestService, MembersService membersService) {
        this.requestService = requestService;
        this.membersService = membersService;
    }

    @PostMapping("/request")
    public ResponseEntity<PostRequestResponse> addNewRequest(@RequestBody PostRequestRequest dto, Authentication auth){
        System.out.println(dto);
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
    public Map<String, List<GetAllRequestsResponse>> getAllResponse(){
        List<GetAllRequestsResponse> theRequests = requestService.findAllRequests()
                .stream().map(GetAllRequestsResponse::from).toList();

        return Map.of("requestList", theRequests);
    }

}
