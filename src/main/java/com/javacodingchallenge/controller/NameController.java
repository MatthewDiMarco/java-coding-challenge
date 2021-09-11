package com.javacodingchallenge.controller;

import com.javacodingchallenge.exception.ApiRequestException;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/names")
public class NameController {

    @Autowired
    private final NameService nameService;

    public NameController(NameService nameService) {
        this.nameService = nameService;
    }

    @GetMapping(path = "{lowerPostcode}/{upperPostcode}")
    public ResponseEntity<?> getNamesInRange(
            @PathVariable(name = "lowerPostcode") String lowerPostcodeStr,
            @PathVariable(name = "upperPostcode") String upperPostcodeStr) throws ApiRequestException {
        NameSearchResult result;
        try {
            result = nameService.getNamesInRange(
                    lowerPostcodeStr, upperPostcodeStr);

        } catch(IllegalStateException | IllegalArgumentException e) {
            throw new ApiRequestException(e.getMessage());
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

}
