package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.exception.AlreadyExistedEntityException;
import com.smartcity.smart_city_information_system.service.CityService;
import com.smartcity.smart_city_information_system.service.MembersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class CityController {

    private CityService cityService;
    private MembersService membersService;

    @Autowired
    public CityController(CityService cityService,
                          MembersService membersService) {
        this.cityService = cityService;
        this.membersService = membersService;
    }

    @PostMapping("/city")
    public ResponseEntity<PostCityResponse> createCity(@Valid @RequestBody PostCityRequest dto, Authentication auth){

        List<City> allCreatedCity = cityService.findAllByUserId(auth.getName());
        boolean cityAlreadyExisted = allCreatedCity.stream().anyMatch(
                c -> c.getCity().equalsIgnoreCase(dto.getCity())
        );
        if(cityAlreadyExisted){
            throw new AlreadyExistedEntityException("City already existed");
        }

        City city = new City(dto.getCity(), "test.jpg", dto.getDescription());
        Members member = membersService.findById(auth.getName());
        city.setMembers(member);
        cityService.addNew(city);
        return ResponseEntity.ok(new PostCityResponse(city.getCity()));
    }

    @PatchMapping("/city")
    public ResponseEntity<PatchCityResponse> updateCity(@RequestBody PatchCityRequest dto){
        try {
            City patchedCity = cityService.patchCity(dto);
            return ResponseEntity.ok(new PatchCityResponse(patchedCity.getCity()));
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(new PatchCityResponse("No changes. Try again."));
        }
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
}
