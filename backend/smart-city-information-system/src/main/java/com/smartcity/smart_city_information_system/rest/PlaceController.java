package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.DeletePlaceResponse;
import com.smartcity.smart_city_information_system.dto.GetAllPlacesResponse;
import com.smartcity.smart_city_information_system.dto.PostPlaceRequest;
import com.smartcity.smart_city_information_system.dto.PostPlaceResponse;
import com.smartcity.smart_city_information_system.service.PlaceService;
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
public class PlaceController {

    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping("/place")
    public ResponseEntity<PostPlaceResponse> createPlace(@Valid @RequestBody PostPlaceRequest dto,
                                                         Authentication auth){
        placeService.addNewPlace(dto, auth.getName());
        return ResponseEntity.ok(new PostPlaceResponse("place is created successfully."));
    }

    @GetMapping("/placeList")
    public ResponseEntity<Map<String, List<GetAllPlacesResponse>>> getPlaces(Authentication auth){
        List<GetAllPlacesResponse> places = placeService.findAllPlacesByUserId(auth.getName());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("places", places));
    }

    @DeleteMapping("/placeList/{placeId}")
    public ResponseEntity<DeletePlaceResponse> deletePlace(@PathVariable @Pattern(regexp = "\\d+") String placeId,
                                                           Authentication auth){
        String removedPlace = placeService.deletePlace(placeId, auth.getName());
        return ResponseEntity.ok(new DeletePlaceResponse(removedPlace));
    }

}
