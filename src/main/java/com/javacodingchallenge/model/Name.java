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
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Column(name = "postcode")
    @JsonView(View.NameDetailView.class)
    @Pattern(
            regexp = "[0-9]{4}",
            message = "Postcodes must be 4 characters with digits only (e.g. 6076)"
    )
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
        int lower, upper, thisCode;
        try {
            lower = Integer.parseInt(lowerPostcode);
            upper = Integer.parseInt(upperPostcode);
            thisCode = Integer.parseInt(postcode);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Failed to parse postcode, " + e.getMessage());
        }

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
