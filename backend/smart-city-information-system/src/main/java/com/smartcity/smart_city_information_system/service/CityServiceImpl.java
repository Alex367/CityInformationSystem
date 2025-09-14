package com.smartcity.smart_city_information_system.service;

import com.smartcity.smart_city_information_system.dao.CityDAO;
import com.smartcity.smart_city_information_system.dto.GetAllCitiesResponse;
import com.smartcity.smart_city_information_system.dto.PatchCityRequest;
import com.smartcity.smart_city_information_system.dto.PostCityRequest;
import com.smartcity.smart_city_information_system.entity.City;
import com.smartcity.smart_city_information_system.entity.Members;
import com.smartcity.smart_city_information_system.exception.AlreadyExistedEntityException;
import com.smartcity.smart_city_information_system.exception.DatabaseOperationException;
import com.smartcity.smart_city_information_system.exception.NotFoundException;
import com.smartcity.smart_city_information_system.exception.UnauthorizedException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService{

    private CityDAO cityDAO;
    private MembersService membersService;

    @Autowired
    public CityServiceImpl(CityDAO cityDAO, MembersService membersService) {
        this.cityDAO = cityDAO;
        this.membersService = membersService;
    }

    @Override
    public List<City> findAll() {
        return cityDAO.findAllCity();
    }

    @Transactional
    @Override
    public void addNewCity(PostCityRequest dto, String userId) {
        List<City> allCreatedCity = cityDAO.findAllByUserId(userId);
        boolean cityAlreadyExisted = allCreatedCity.stream().anyMatch(
                c -> c.getCity().equalsIgnoreCase(dto.getCity())
        );
        if(cityAlreadyExisted){
            throw new AlreadyExistedEntityException("City already existed");
        }

        Members member = membersService.findById(userId);

        City city = new City(dto.getCity(), "test.jpg", dto.getDescription());
        city.setMembers(member);
        try{
            cityDAO.addNewCity(city);
        } catch (PersistenceException e) {
            throw new DatabaseOperationException("Failed to save city "
                    + dto.getCity()
                    + " due to db error", e);
        }

    }

    @Transactional
    @Override
    public String deleteCity(String cityId, String userId){
        try {
            City theCity = cityDAO.findById(cityId);
            if(theCity == null){
                throw new NotFoundException("City with id " + cityId + " not found");
            }
            if(!theCity.getMembers().getUser_id().equals(userId)){
                throw new UnauthorizedException("User not authorized to delete city " + userId);
            }
            return cityDAO.deleteCity(theCity);
        }
        catch (PersistenceException e){
            throw new DatabaseOperationException("Failed to delete city "
                    + cityId
                    + " due to db error", e);
        }
    }

    @Override
    public City findById(String id) {
        return cityDAO.findById(id);
    }

    @Override
    public List<GetAllCitiesResponse> findAllByUserId(String userId) {
        try{
            return cityDAO.findAllByUserId(userId).stream()
                .map(GetAllCitiesResponse::from)
                .toList();
        }catch (PersistenceException e){
            throw new DatabaseOperationException("Failed to retrieve cities for user "
                    + userId
                    + " due to db error", e);
        }
    }

    @Override
    @Transactional
    public City patchCity(PatchCityRequest dto) {
        City city = cityDAO.findById(dto.getId());
        if(city == null){
            throw new NotFoundException("City with the name " + dto.getCity() + " not found");
        }

        if(city.getCity().equals(dto.getCity())
                && city.getDescription().equals(dto.getDescription())
                && "test.jpg".equals(city.getPath_file())
        ) {
            throw new AlreadyExistedEntityException("No changes detected");
        }

        city.setCity(dto.getCity());
        city.setDescription(dto.getDescription());
        city.setPath_file("test.jpg");

        return city;
    }

    @Override
    public City findByCityName(String cityName) {
        return cityDAO.findByCityName(cityName);
    }

    @Override
    public City findByCityNameUserId(String userId, String cityName) {
        return cityDAO.findByCityNameUserId(userId, cityName);
    }
}
