package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.GetAllPlacesResponse;
import com.smartcity.smart_city_information_system.dto.PostPlaceRequest;
import com.smartcity.smart_city_information_system.dto.PostPlaceResponse;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.entity.Place;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.service.CityService;
import com.smartcity.smart_city_information_system.service.PlaceService;
import com.smartcity.smart_city_information_system.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class PlaceController {

    private PlaceService placeService;
    private CityService cityService;
    private TypeService typeService;

    @Autowired
    public PlaceController(PlaceService placeService, CityService cityService, TypeService typeService) {
        this.placeService = placeService;
        this.cityService = cityService;
        this.typeService = typeService;
    }

    @PostMapping("/place")
    public ResponseEntity<PostPlaceResponse> createPlace(@RequestBody PostPlaceRequest dao){
        Place thePlace = new Place(dao.getPlace(), dao.getDescription());

        City theCity = cityService.findByCityName(dao.getCity());
        Type theType = typeService.findByTypeName(dao.getType());

        thePlace.setTheCity(theCity);
        thePlace.setTheType(theType);
        placeService.addNewPlace(thePlace);

        return ResponseEntity.ok(new PostPlaceResponse("created!"));
    }

    @GetMapping("/placeList")
    public Map<String, List<GetAllPlacesResponse>> getPlaces(Authentication auth){
        System.out.println(auth.getName());
        List<GetAllPlacesResponse> places = placeService.findAllPlaces()
                .stream().map(GetAllPlacesResponse::from).toList();

        return Map.of("places", places);
    }

}
