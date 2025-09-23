package com.smartcity.smart_city_information_system.rest;

import com.smartcity.smart_city_information_system.dto.*;
import com.smartcity.smart_city_information_system.entity.Type;
import com.smartcity.smart_city_information_system.service.TypeService;
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
public class TypeController {

    private TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/type")
    public ResponseEntity<PostTypeResponse> addNewType(@Valid @RequestBody PostTypeRequest dto,
                                                       Authentication auth) {
        typeService.addNewType(dto, auth);
        return ResponseEntity.ok(new PostTypeResponse(dto.getTypename()));
    }

    @PatchMapping("/type")
    public ResponseEntity<PatchTypeResponse> pathType(@Valid @RequestBody PatchTypeRequest dto,
                                                      Authentication auth) {
        Type patchedType = typeService.patchType(dto, auth);
        return ResponseEntity.ok(new PatchTypeResponse(patchedType.getType()));
    }

    @GetMapping("/typeList")
    public ResponseEntity<Map<String, List<GetAllTypesResponse>>> getAllTypes() {
        List<GetAllTypesResponse> allTypes = typeService.findAllTypes();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("types", allTypes));
    }

    @DeleteMapping("/typeList/{typeId}")
    public ResponseEntity<DeleteTypeResponse> deleteType(@PathVariable @Pattern(regexp = "\\d+") String typeId,
                                                         Authentication auth) {
        String deletedType = typeService.deleteType(typeId, auth);
        return ResponseEntity.ok(new DeleteTypeResponse(deletedType));
    }

}
