package com.javacodingchallenge.service;

import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.repository.NameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class NameService {

    @Autowired
    private final NameRepository nameRepository;

    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    public List<Name> getAllNames() {
        return (List<Name>) nameRepository.findAll();
    }

    public Name getNameById(Long nameId) {
        Optional<Name> name = nameRepository.findById(nameId);
        if (name.isEmpty()) {
            throw new IllegalArgumentException("No such Name with ID " + nameId);
        }

        return name.get();
    }

    public NameSearchResult getNamesInRange(String lowerPostcode, String upperPostcode) {
        // fetch all names
        List<Name> allNames = (List<Name>) nameRepository.findAll();

        // process names
        List<Name> processedNames = allNames.stream()
                .filter(name -> name.inPostcodeRange(lowerPostcode, upperPostcode))
                .sorted(Comparator.comparing(Name::getName))
                .collect(Collectors.toList());

        // count chars
        AtomicInteger charCounter = new AtomicInteger(0);
        processedNames.forEach(name -> charCounter.addAndGet(name.getName().length()));

        return new NameSearchResult(charCounter.get(), processedNames);
    }

    public List<Name> saveNames(List<Name> names) {
        List<Name> savedNames = (List<Name>) nameRepository.saveAll(names);
        return savedNames;
    }

}
