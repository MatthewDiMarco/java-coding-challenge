package com.javacodingchallenge.model;

import javax.persistence.*;

@Entity
@Table(name = "Name")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nameId;

    @Column(name = "name")
    private String name;

    @Column(name = "postcode")
    private Postcode postcode;

    public Name() {
        this.name = "none";
        this.postcode = new Postcode("0000");
    }

    public Name(String name, Postcode postcode) {
        this.name = name;
        this.postcode = postcode;
    }

    public Long getNameId() {
        return nameId;
    }

    public String getName() {
        return name;
    }

    public Postcode getPostcode() {
        return postcode;
    }

    public boolean inPostcodeRange(Postcode lowerPostcode, Postcode upperPostcode) {
        // get codes as integers (e.g. "0000" -> 0)
        int lower = Integer.parseInt(lowerPostcode.getCode());
        int upper = Integer.parseInt(upperPostcode.getCode());
        int thisCode = Integer.parseInt(postcode.getCode());

        // check range
        boolean valid = false;
        if (thisCode > lower && thisCode < upper) {
            valid = true;
        }

        return valid;
    }
}
