package com.smartcity.smart_city_information_system.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartcity.smart_city_information_system.dto.DeleteCityResponse;
import com.smartcity.smart_city_information_system.dto.RegistrationRequest;
import com.smartcity.smart_city_information_system.dto.RegistrationResponse;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.service.CityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CityRestController {

    private CityService cityService;
    private ObjectMapper objectMapper;
    private final JdbcUserDetailsManager userDetailsManager;

    @Autowired
    public CityRestController(CityService cityService,
                              ObjectMapper theObjectMapper,
                              JdbcUserDetailsManager userDetailsManager) {
        this.cityService = cityService;
        objectMapper = theObjectMapper;
        this.userDetailsManager = userDetailsManager;
    }

    @GetMapping("/cityList")
    public Map<String, List<City>> getCity(){
        return Map.of("cities", cityService.findAll());
    }

    @DeleteMapping("/cityList/{cityId}")
    public ResponseEntity<DeleteCityResponse> deleteCity(@PathVariable String cityId){
        String deletedCity = cityService.deleteCity(cityId);
        return ResponseEntity.ok(new DeleteCityResponse(deletedCity));
    }

    @PostMapping("/city")
    public Map<String, String> createCity(@RequestBody City theCity){
        System.out.println(theCity.toString());
        theCity.setId(0);
        theCity.setPath_file("test.jpg");
        cityService.addNew(theCity);
        return Map.of("createdCity", theCity.getCity());
    }

    @PatchMapping("/city")
    public Map<String, String> updateCity(@RequestBody Map<String, String> payload){
        System.out.println(payload.toString());
        City tempCity = cityService.findById(payload.get("id"));
        String changedCity = tempCity.getCity();

        ObjectNode convertedTempCity = objectMapper.convertValue(tempCity, ObjectNode.class);
        ObjectNode convertedPayload = objectMapper.convertValue(payload, ObjectNode.class);

        convertedTempCity.setAll(convertedPayload);

        City patchedCity = objectMapper.convertValue(convertedTempCity, City.class);
        patchedCity.setPath_file("test.jpg");
        cityService.addNew(patchedCity);

        return Map.of("patchedCity", changedCity);

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

        UserDetails user = User.builder()
                .username(req.getUser_id())
                .password("{noop}" + req.getPw())
                .roles("EMPLOYEE")
                .build();

        userDetailsManager.createUser(user);

        return ResponseEntity.ok(new RegistrationResponse("User registered successfully!!!", req.getUser_id()));

    }
}
