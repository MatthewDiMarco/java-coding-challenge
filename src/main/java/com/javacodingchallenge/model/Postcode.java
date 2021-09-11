package com.javacodingchallenge.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
public class Postcode {

    @Pattern(regexp = "[0-9]{4}")
    private String code;

    public Postcode() {
        this.code = "0000";
    }

    public Postcode(String postcode) {
        if (postcode.length() == 4) {
            for (char ch : postcode.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    throw new IllegalStateException("Postcode should only contain digits");
                }
            }
        } else {
            throw new IllegalStateException(
                    "Postcode should be a 4-digit number (read " + postcode.length() + ")");
        }

        this.code = postcode;
    }

    public String getCode() {
        return code;
    }

}
