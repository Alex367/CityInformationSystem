package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.PlaceDAO;
import com.smartcity.smart_city_information_system.dto.GetAllPlacesResponse;
import com.smartcity.smart_city_information_system.dto.PostPlaceRequest;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.entity.Place;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.exception.AlreadyExistedEntityException;
import com.smartcity.smart_city_information_system.exception.DatabaseOperationException;
import com.smartcity.smart_city_information_system.exception.NotFoundException;
import com.smartcity.smart_city_information_system.exception.UnauthorizedException;
import com.smartcity.smart_city_information_system.mapstruct.PlaceMapper;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService{
    private PlaceDAO placeDAO;
    private CityService cityService;
    private TypeService typeService;
    private PlaceMapper placeMapper;

    @Autowired
    public PlaceServiceImpl(PlaceDAO placeDAO,
                            CityService cityService,
                            TypeService typeService,
                            PlaceMapper placeMapper) {
        this.placeDAO = placeDAO;
        this.cityService = cityService;
        this.typeService = typeService;
        this.placeMapper = placeMapper;
    }

    @Override
    public List<Place> findAllPlaces() {
        return placeDAO.findAllPlaces();
    }

    @Transactional
    @Override
    public void addNewPlace(PostPlaceRequest dto, String userId) {
        try{
            City theCity = cityService.findCityIdByUserCityName(dto.getCity(), userId);
            if(theCity == null){
                throw new NotFoundException("City with the name " + dto.getCity()
                        + " and user " + userId + " not found");
            }

            Type theType = typeService.findByTypeName(dto.getType());
            if (theType == null) {
                throw new NotFoundException("Type with the name " + dto.getType()
                        + " not found");
            }

            boolean placeAlreadyExisted = placeDAO.existsByPlaceAndCityAndType(
                    dto.getPlace(),
                    dto.getCity(),
                    dto.getType(),
                    userId
            );
            if(placeAlreadyExisted){
                throw new AlreadyExistedEntityException("Place already exists");
            }

            Place thePlace = new Place(dto.getPlace(), "test.jpg", dto.getDescription());
            thePlace.setTheCity(theCity);
            thePlace.setTheType(theType);
            placeDAO.addNewPlace(thePlace);

        } catch (PersistenceException e){
                throw new DatabaseOperationException("Failed perform operations due to db error", e);
        }
    }

    @Override
    public List<GetAllPlacesResponse> findAllPlacesByUserId(String userId) {
        try {
            List<Place> allPlaces = placeDAO.findAllPlacesByUserId(userId);
            return placeMapper.toGetAllPlacesResponseList(allPlaces);
        } catch (PersistenceException e){
            throw new DatabaseOperationException("Failed to retrieve places for user "
                    + userId
                    + " due to db error", e);
        }
    }

    @Transactional
    @Override
    public String deletePlace(String placeId, String userId) {
        try{
            Place thePlace = placeDAO.findPlaceById(placeId);
            if(thePlace == null){
                throw new NotFoundException("Place with id " + placeId + " not found");
            }

            if(!thePlace.getTheCity().getMembers().getUser_id().equals(userId)){
                throw new UnauthorizedException("User not authorized to delete place.");
            }
            return placeDAO.deletePlace(thePlace);

        } catch (PersistenceException e){
            throw new DatabaseOperationException("Failed to delete place "
                    + placeId
                    + " due to db error", e);
        }
    }

    @Override
    public Place findPlaceById(String placeId) {
        return placeDAO.findPlaceById(placeId);
    }
}
