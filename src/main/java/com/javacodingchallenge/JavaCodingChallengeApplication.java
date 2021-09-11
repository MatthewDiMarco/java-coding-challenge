package com.javacodingchallenge;

import com.javacodingchallenge.model.Name;
import com.javacodingchallenge.model.Postcode;
import com.javacodingchallenge.service.NameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JavaCodingChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaCodingChallengeApplication.class, args);
    }

    @Bean
    CommandLineRunner run(NameService nameService) {
        return args -> {
            List<Name> testList = new ArrayList<>();

            // test data
            testList.add(new Name("Lesmurdie","6076"));
            testList.add(new Name("Kalamunda","6076"));
            testList.add(new Name("Bentley","6102"));

            // populate database
            nameService.saveNames(testList);
        };
    }

}
