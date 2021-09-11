package com.javacodingchallenge.service;

import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.repository.NameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class NameServiceTest {

    List<Name> names;
    NameSearchResult nameSearchResult;

    @Mock
    NameRepository nameRepository;

    @InjectMocks
    NameService nameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        names = new ArrayList<Name>();
        names.add(new Name("Midland", "6056"));
        names.add(new Name("Cannington", "6107"));
        nameSearchResult = new NameSearchResult(17, names);
    }

    @Test
    void getAllNames_Valid() {
        try {
            when(nameRepository.findAll()).thenReturn(names);
            List<Name> result = nameService.getAllNames();
            Assertions.assertEquals(result, names);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNameById_Valid() {
        try {
            when(nameRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(names.get(0)));
            Name result = nameService.getNameById(1L);
            Assertions.assertEquals(result, names.get(0));
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNameById_NoSuchId() {
        try {
            when(nameRepository.findById(any(Long.class))).thenReturn(Optional.empty());
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                nameService.getNameById(1L);
            });
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNamesInRange_Valid() {
        try {
            when(nameRepository.findAll()).thenReturn(names);
            NameSearchResult result = nameService.getNamesInRange(
                    "6000", "7000");
            Assertions.assertEquals(result.getNumChars(), nameSearchResult.getNumChars());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNamesInRange_EqualRange() {
        try {
            when(nameRepository.findAll()).thenReturn(names);
            NameSearchResult result = nameService.getNamesInRange(
                    "6000", "6000");
            Assertions.assertEquals(result.getNumChars(), 0);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNamesInRange_BackwardsRange() {
        try {
            when(nameRepository.findAll()).thenReturn(names);
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                NameSearchResult result = nameService.getNamesInRange(
                        "7000", "6000");
            });
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void saveNames_Valid() {
        try {
            when(nameRepository.saveAll(anyList())).thenReturn(names);
            List<Name> result = nameService.saveNames(names);
            Assertions.assertEquals(result, names);
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}