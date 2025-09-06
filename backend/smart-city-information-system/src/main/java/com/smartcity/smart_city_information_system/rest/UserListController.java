package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.DeleteUserResponse;
import com.smartcity.smart_city_information_system.dto.UserListResponse;
import com.smartcity.smart_city_information_system.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserListController {

    private RolesService rolesService;

    @Autowired
    public UserListController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/userList")
    public Map<String, List<UserListResponse>> getUsers(Authentication auth){
        List<UserListResponse> allUsers = rolesService
                .findAllUsers(auth.getName()).stream().map(UserListResponse::from).toList();
        return Map.of("users", allUsers);
    }

    @DeleteMapping("/userList/{userId}")
    public ResponseEntity<DeleteUserResponse> deleteUsers(@PathVariable String userId){
        String removedUser = rolesService.deleteUser(userId);
        return ResponseEntity.ok(new DeleteUserResponse(removedUser));
    }

}
