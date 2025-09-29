package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.DeleteUserResponse;
import com.smartcity.smart_city_information_system.dto.GetUserListResponse;
import com.smartcity.smart_city_information_system.service.RolesService;
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
public class UserListController {

    private RolesService rolesService;

    @Autowired
    public UserListController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping("/userList")
    public ResponseEntity<Map<String, List<GetUserListResponse>>> getUsers(Authentication auth) {
        List<GetUserListResponse> allUsers = rolesService.findAllUsers(auth);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("users", allUsers));
    }

    @DeleteMapping("/userList/{userId}")
    public ResponseEntity<DeleteUserResponse> deleteUsers(@PathVariable @Pattern(regexp = "\\d+") String userId,
                                                          Authentication auth) {
        String removedUser = rolesService.deleteUser(userId, auth);
        return ResponseEntity.ok(new DeleteUserResponse(removedUser));
    }

}
