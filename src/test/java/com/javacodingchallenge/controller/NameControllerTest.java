package com.javacodingchallenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.NameSearchResult;
import com.javacodingchallenge.service.NameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(NameController.class)
class NameControllerTest {

    List<Name> names;
    NameSearchResult nameSearchResult;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    NameService nameService;

    @BeforeEach
    void setUp() {
        names = new ArrayList<Name>();
        names.add(new Name("Midland", "6056"));
        names.add(new Name("Cannington", "6107"));
        nameSearchResult = new NameSearchResult(17, names);
    }

    @Test
    void getAllNames_Valid() {
        try {
            when(nameService.getAllNames()).thenReturn(names);
            ResultActions perform = mockMvc.perform(get("/names"));
            perform.andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNameById_Valid() {
        try {
            when(nameService.getNameById(any(Long.class))).thenReturn(names.get(0));
            ResultActions perform = mockMvc.perform(get("/names/1"));
            perform.andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNameById_IllegalArgumentException() {
        try {
            when(nameService.getNameById(any(Long.class))).thenThrow(IllegalArgumentException.class);
            ResultActions perform = mockMvc.perform(get("/names/2"));
            perform.andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void getNamesInRange_Valid() {
        try {
            when(nameService.getNamesInRange(any(String.class), any(String.class)))
                    .thenReturn(nameSearchResult);
            ResultActions perform = mockMvc.perform(get("/names/6000/7000"));
            perform.andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void addNames_Valid() {
        try {
            when(nameService.saveNames(anyList())).thenReturn(names);
            ResultActions perform = mockMvc.perform(post("/names")
                    .content(mapper.writeValueAsString(names))
                    .contentType(MediaType.APPLICATION_JSON));
            perform.andExpect(status().isOk());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void addNames_DataIntegrityViolationException() {
        try {
            when(nameService.saveNames(anyList())).thenThrow(DataIntegrityViolationException.class);
            ResultActions perform = mockMvc.perform(post("/names")
                    .content(mapper.writeValueAsString(names))
                    .contentType(MediaType.APPLICATION_JSON));
            perform.andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    void addNames_ConstraintViolationException() {
        try {
            Set<ConstraintViolation<?>> mockViolations = new HashSet<>();
            ConstraintViolationException mockedException = mock(ConstraintViolationException.class);
            when(mockedException.getConstraintViolations()).thenReturn(mockViolations);
            when(nameService.saveNames(anyList())).thenThrow(mockedException);
            ResultActions perform = mockMvc.perform(post("/names")
                    .content(mapper.writeValueAsString(names))
                    .contentType(MediaType.APPLICATION_JSON));
            perform.andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assertions.fail(e.getMessage());
        }
    }
}