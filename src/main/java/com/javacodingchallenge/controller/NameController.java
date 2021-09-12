package com.javacodingchallenge.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacodingchallenge.View;
import com.javacodingchallenge.exception.ApiRequestException;
import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    @JsonView(View.GeneralView.class)
    public ResponseEntity<?> getAllNames() {
        List<Name> names = nameService.getAllNames();
        return new ResponseEntity<>(names, HttpStatus.OK);
    }

    @GetMapping(path = "{nameId}")
    @JsonView({View.NameDetailView.class})
    public ResponseEntity<?> getNameById(@PathVariable(name = "nameId") Long nameId) throws ApiRequestException {
        Name name;
        try {
            name = nameService.getNameById(nameId);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(name);
    }

    @GetMapping(path = "{lowerPostcode}/{upperPostcode}")
    @JsonView(View.GeneralView.class)
    public ResponseEntity<?> getNamesInRange(
            @PathVariable(name = "lowerPostcode") String lowerPostcode,
            @PathVariable(name = "upperPostcode") String upperPostcode) throws ApiRequestException {
        NameSearchResult result;
        try {
            result = nameService.getNamesInRange(
                    lowerPostcode, upperPostcode);
        } catch(IllegalArgumentException e) {
            throw new ApiRequestException(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping
    @JsonView(View.GeneralView.class)
    public ResponseEntity<?> addNames(@RequestBody List<Name> newNames) throws ApiRequestException {
        try {
            nameService.saveNames(newNames);
        } catch(ConstraintViolationException e) {
            StringBuilder stringBuilder = new StringBuilder();
            e.getConstraintViolations()
                    .forEach(ex -> {
                        stringBuilder.append(ex.getMessageTemplate());
                    });

            throw new ApiRequestException(stringBuilder.toString());
        } catch (DataIntegrityViolationException e) {
            throw new ApiRequestException(
                    "Data Integrity Violation: " +
                    "the provided name may already exist in the database");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newNames);
    }

}
