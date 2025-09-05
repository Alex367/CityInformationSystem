package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.DeleteTypeResponse;
import com.smartcity.smart_city_information_system.dto.GetAllTypesResponse;
import com.smartcity.smart_city_information_system.dto.PostTypeRequest;
import com.smartcity.smart_city_information_system.dto.PostTypeResponse;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200"})
public class TypeController {

    private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/type")
    public ResponseEntity<PostTypeResponse> addNewType(@RequestBody PostTypeRequest dao){

        Type theType = new Type(dao.getTypename(), "test.jpg", dao.getDescription());

        typeService.addNewType(theType);
        return ResponseEntity.ok(new PostTypeResponse(dao.getTypename()));
    }

    @GetMapping("/typeList")
    public ResponseEntity<GetAllTypesResponse> addNewType(){
        List<Type> res = typeService.findAllTypes();
        return ResponseEntity.ok(new GetAllTypesResponse(res));
    }

    @DeleteMapping("/typeList/{typeId}")
    public ResponseEntity<DeleteTypeResponse> deleteType(@PathVariable String typeId){
        String deletedType = typeService.deleteType(typeId);
        return ResponseEntity.ok(new DeleteTypeResponse(deletedType));
    }
}
