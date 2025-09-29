package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.service.CityService;
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
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/city")
    public ResponseEntity<PostCityResponse> createCity(@Valid @RequestBody PostCityRequest dto,
                                                       Authentication auth){
        cityService.addNewCity(dto, auth.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new PostCityResponse(dto.getCity()));
    }

    @PatchMapping("/city")
    public ResponseEntity<PatchCityResponse> patchCity(@Valid @RequestBody PatchCityRequest dto){
        City patchedCity = cityService.patchCity(dto);
        return ResponseEntity.ok(new PatchCityResponse(patchedCity.getId(), patchedCity.getCity()));
    }

    @GetMapping("/cityList")
    public ResponseEntity<Map<String, List<GetAllCitiesResponse>>> getCity(Authentication auth){
        String currentUsername = auth.getName();
        List<GetAllCitiesResponse> allCities = cityService.findAllByUserId(currentUsername);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("cities", allCities));
    }

    @DeleteMapping("/cityList/{cityId}")
    public ResponseEntity<DeleteCityResponse> deleteCity(@PathVariable @Pattern(regexp = "\\d+") String cityId,
                                                         Authentication auth){
        String deletedCity = cityService.deleteCity(cityId, auth.getName());
        return ResponseEntity.ok(new DeleteCityResponse(deletedCity));
    }
}
