package com.javacodingchallenge.controller;

import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/names")
public class NameController {

    @Autowired
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    // todo: exception handling
    @GetMapping(path = "{lowerPostcode}/{upperPostcode}")
    public ResponseEntity<?> getNamesInRange(
            @PathVariable(name = "lowerPostcode") String lowerPostcodeStr,
            @PathVariable(name = "upperPostcode") String upperPostcodeStr
    ) {
        List<Name> names = nameService.getNamesInRange(
                lowerPostcodeStr, upperPostcodeStr);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(names);
    }

}
