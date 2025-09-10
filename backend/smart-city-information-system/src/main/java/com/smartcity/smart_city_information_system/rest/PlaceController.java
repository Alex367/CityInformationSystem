package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.DeletePlaceResponse;
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
    public ResponseEntity<PostPlaceResponse> createPlace(@RequestBody PostPlaceRequest dto){
        Place thePlace = new Place(dto.getPlace(), dto.getDescription());

        City theCity = cityService.findByCityName(dto.getCity());
        Type theType = typeService.findByTypeName(dto.getType());

        List<Place> allCreatedPlaces = placeService.findAllPlaces();
        boolean placeAlreadyExisted = allCreatedPlaces.stream().anyMatch(
                p -> p.getPlace().equalsIgnoreCase(dto.getPlace()) &&
                        p.getTheCity().getCity().equalsIgnoreCase(dto.getCity()) &&
                        p.getTheType().getType().equalsIgnoreCase(dto.getType())
        );
        if(placeAlreadyExisted){
            return ResponseEntity.badRequest().body(new PostPlaceResponse("Place already exists"));
        }

        thePlace.setTheCity(theCity);
        thePlace.setTheType(theType);
        placeService.addNewPlace(thePlace);

        return ResponseEntity.ok(new PostPlaceResponse("created!"));
    }

    @GetMapping("/placeList")
    public Map<String, List<GetAllPlacesResponse>> getPlaces(Authentication auth){
        List<GetAllPlacesResponse> places = placeService.findAllPlacesByUserId(auth.getName())
                .stream().map(GetAllPlacesResponse::from).toList();

        return Map.of("places", places);
    }

    @DeleteMapping("/placeList/{placeId}")
    public ResponseEntity<DeletePlaceResponse> deletePlace(@PathVariable String placeId){
        String removedPlace = placeService.deletePlace(placeId);
        return ResponseEntity.ok(new DeletePlaceResponse(removedPlace));
    }

}
