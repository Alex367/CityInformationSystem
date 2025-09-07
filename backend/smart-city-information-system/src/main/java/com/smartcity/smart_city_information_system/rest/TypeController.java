package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<PostTypeResponse> addNewType(@RequestBody PostTypeRequest dto){

        if(dto.getTypename() == null){
            return ResponseEntity.badRequest().body(new PostTypeResponse("Type name cannot be empty"));
        }

        List<Type> allCreatedTypes = typeService.findAllTypes();
        boolean typeAlreadyExisted = allCreatedTypes.stream().anyMatch(
                t -> t.getType().equalsIgnoreCase(dto.getTypename())
        );
        if(typeAlreadyExisted){
            return ResponseEntity.badRequest().body(new PostTypeResponse("Type already exists"));
        }

        Type theType = new Type(dto.getTypename(), "test.jpg", dto.getDescription());

        typeService.addNewType(theType);
        return ResponseEntity.ok(new PostTypeResponse(dto.getTypename()));
    }

    @PatchMapping("/type")
    public ResponseEntity<PatchTypeResponse> pathType(@RequestBody PatchTypeRequest dao, Authentication auth){
        if(!checkIfIsAdmin(auth)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new PatchTypeResponse("User " + auth.getName() + " is forbidden to delete this type"));
        }

        try{
            Type patchedType = typeService.patchType(dao);
            return ResponseEntity.ok(new PatchTypeResponse(patchedType.getType()));
        } catch (IllegalStateException e){
            return ResponseEntity.badRequest().body(new PatchTypeResponse("No changes. Try again."));
        }
    }

    @GetMapping("/typeList")
    public Map<String, List<GetAllTypesResponse>> addNewType(){
        List<GetAllTypesResponse> allTypes = typeService.findAllTypes()
                .stream().map(GetAllTypesResponse::from)
                .toList();
        return Map.of("types", allTypes);
    }

    @DeleteMapping("/typeList/{typeId}")
    public ResponseEntity<DeleteTypeResponse> deleteType(@PathVariable String typeId, Authentication auth){
        if(!checkIfIsAdmin(auth)){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new DeleteTypeResponse("User " + auth.getName() + " is forbidden to delete this type"));
        }

        String deletedType = typeService.deleteType(typeId);
        return ResponseEntity.ok(new DeleteTypeResponse(deletedType));
    }

    public Boolean checkIfIsAdmin(Authentication auth){
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

}
