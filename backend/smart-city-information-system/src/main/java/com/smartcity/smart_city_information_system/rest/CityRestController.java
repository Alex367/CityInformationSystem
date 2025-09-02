package com.smartcity.smart_city_information_system.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.service.CityService;
import com.smartcity.smart_city_information_system.service.MembersService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CityRestController {

    private CityService cityService;
    private MembersService membersService;
    private ObjectMapper objectMapper;
    private final JdbcUserDetailsManager userDetailsManager;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CityRestController(CityService cityService,
                              ObjectMapper theObjectMapper,
                              JdbcUserDetailsManager userDetailsManager,
                              PasswordEncoder passwordEncoder,
                              MembersService membersService) {
        this.cityService = cityService;
        objectMapper = theObjectMapper;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.membersService = membersService;
    }

    @GetMapping("/cityList")
    public Map<String, List<CityListDTO>> getCity(Authentication auth){
        String currentUsername = auth.getName();
        List<CityListDTO> dtos = cityService.findAllByUserId(currentUsername).stream()
            .map(CityListDTO::from)
            .toList();
        return Map.of("cities", dtos);
    }

    @DeleteMapping("/cityList/{cityId}")
    public ResponseEntity<DeleteCityResponse> deleteCity(@PathVariable String cityId){
        String deletedCity = cityService.deleteCity(cityId);
        return ResponseEntity.ok(new DeleteCityResponse(deletedCity));
    }

    @PostMapping("/city")
    public ResponseEntity<PostCityResponse> createCity(@RequestBody PostCityRequest dto, Authentication auth){
        City city = new City(dto.getCity(), "test.jpg", dto.getDescription());
        Members member = membersService.findById(auth.getName());
        city.setMembers(member);
        cityService.addNew(city);
        return ResponseEntity.ok(new PostCityResponse(city.getCity()));
    }

    @PatchMapping("/city")
    public ResponseEntity<PatchCityResponse> updateCity(@RequestBody PatchCityRequest dto){
        City patchedCity = cityService.patchCity(dto);
        return ResponseEntity.ok(new PatchCityResponse(patchedCity.getCity()));
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
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequest req){
        System.out.println(req.toString());

        if(userDetailsManager.userExists((req.getUser_id()))){
            return ResponseEntity.badRequest().body(new RegistrationResponse("User already exists", req.getUser_id()));
        }

        String encocedPassword = passwordEncoder.encode(req.getPw());

        UserDetails user = User.builder()
                .username(req.getUser_id())
                .password(encocedPassword)
                .roles("EMPLOYEE")
                .build();

        userDetailsManager.createUser(user);

        return ResponseEntity.ok(new RegistrationResponse("User registered successfully!!!", req.getUser_id()));

    }
}
