package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.service.RequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RequestController {

    private RequestService requestService;

    @Autowired
    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/request")
    public ResponseEntity<PostRequestResponse> addNewRequest(@Valid @RequestBody PostRequestRequest dto,
                                                             Authentication auth){
        requestService.addNewRequest(dto, auth.getName());
        return ResponseEntity.ok(new PostRequestResponse("request is created!"));
    }

    @GetMapping("/requestList")
    public ResponseEntity<Map<String, List<GetAllRequestsResponse>>> getAllResponse(Authentication auth){
        List<GetAllRequestsResponse> theRequests = requestService.findAllRequests(auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("requestList", theRequests));
    }

    @PatchMapping("/request")
    public ResponseEntity<PatchRequestResponse> patchResponse(@Valid @RequestBody PatchRequestRequest dto){
        requestService.patchRequest(dto);
        return ResponseEntity.ok(new PatchRequestResponse("responded", dto.getStatus()));
    }

    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<DeleteRequestResponse> deleteRequest(@PathVariable @Pattern(regexp = "\\d+") String requestId,
                                                               Authentication auth){
        requestService.deleteRequestById(requestId, auth);
        return ResponseEntity.ok(new DeleteRequestResponse(requestId));
    }

}
