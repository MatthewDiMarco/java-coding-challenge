package com.javacodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacodingchallenge.View;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "Name")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.GeneralView.class)
    private Long nameId;

    @Column(name = "name")
    @JsonView(View.GeneralView.class)
    @NotEmpty
    private String name;

    @Column(name = "postcode")
    @JsonView(View.NameDetailView.class)
    @Pattern(regexp = "[0-9]{4}") // 4 digit string
    private String postcode;

    public Name() {
        this.name = "none";
        this.postcode = "0000";
    }

    public Name(String name, String postcode) {
        this.name = name;
        this.postcode = postcode;
    }

    public Long getNameId() {
        return nameId;
    }

    public String getName() {
        return name;
    }

    public String getPostcode() {
        return postcode;
    }

    public boolean inPostcodeRange(String lowerPostcode, String upperPostcode) {
        // get codes as integers (e.g. "0000" -> 0)
        int lower = Integer.parseInt(lowerPostcode);
        int upper = Integer.parseInt(upperPostcode);
        int thisCode = Integer.parseInt(postcode);

        // validate range
        if (lower > upper) {
            throw new IllegalArgumentException(
                    "Lower bound (" + lower + ") " +
                    "is greater than upper (" + upper + ")");
        }

        // check range
        boolean valid = false;
        if (thisCode >= lower && thisCode <= upper) {
            valid = true;
        }

        return valid;
    }
}
