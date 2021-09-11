package com.javacodingchallenge.service;

import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.Postcode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NameService {

    // todo: repository

    public NameService() {
        // todo
    }

    public List<Name> getNamesInRange(String lowerPostcodeStr, String upperPostcodeStr) {
        // todo: get this from repo...
        List<Name> testList = new ArrayList<>();
        testList.add(new Name("Lesmurdie", new Postcode("6076")));
        testList.add(new Name("Kalamunda", new Postcode("6076")));
        testList.add(new Name("Bentley", new Postcode("6102")));

        // validate imported postcodes
        Postcode lowerPostcode = new Postcode(lowerPostcodeStr);
        Postcode upperPostcode = new Postcode(upperPostcodeStr);

        return testList.stream()
                .filter(name -> name.inPostcodeRange(lowerPostcode, upperPostcode))
                .collect(Collectors.toList());
    }

}
