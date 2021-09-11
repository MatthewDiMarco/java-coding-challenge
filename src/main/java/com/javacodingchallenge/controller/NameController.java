package com.javacodingchallenge.controller;

import com.javacodingchallenge.exception.ApiRequestException;
import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping(path = "/names")
public class NameController {

    @Autowired
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping
    public ResponseEntity<?> getAllNames() {
        List<Name> names = nameService.getAllNames();
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping(path = "{lowerPostcode}/{upperPostcode}")
    public ResponseEntity<?> getNamesInRange(
            @PathVariable(name = "lowerPostcode") String lowerPostcode,
            @PathVariable(name = "upperPostcode") String upperPostcode) throws ApiRequestException {
        NameSearchResult result;
        try {
            result = nameService.getNamesInRange(
                    lowerPostcode, upperPostcode);
        } catch(ConstraintViolationException | IllegalArgumentException e) {
            throw new ApiRequestException(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping
    public ResponseEntity<?> addNames(@RequestBody List<Name> newNames) throws ApiRequestException {
        try {
            nameService.saveNames(newNames);
        } catch(ConstraintViolationException | IllegalArgumentException e) {
            throw new ApiRequestException(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newNames);
    }

}
