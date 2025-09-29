package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.PostRegistrationRequest;
import com.smartcity.smart_city_information_system.dto.PostRegistrationResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    private final JdbcUserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JdbcUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user-info")
    public ResponseEntity<?> userInfo(Authentication auth) {
        if (auth == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("message", "Not authenticated"));
        }

        return ResponseEntity.ok(Map.of(
            "username", auth.getName(),
            "roles", auth.getAuthorities()
        ));
    }

    @PostMapping("/registration")
    public ResponseEntity<PostRegistrationResponse> registerUser(@Valid @RequestBody PostRegistrationRequest req){

        if(userDetailsManager.userExists(req.getUser_id())){
            return ResponseEntity.badRequest().body(new PostRegistrationResponse("User already exists",
                    req.getUser_id()));
        }

        String encodedPassword = passwordEncoder.encode(req.getPw());

        UserDetails user = User.builder()
                .username(req.getUser_id())
                .password(encodedPassword)
                .roles("EMPLOYEE")
                .build();

        userDetailsManager.createUser(user);

        return ResponseEntity.ok(new PostRegistrationResponse("User registered successfully.",
                req.getUser_id()));

    }
}
