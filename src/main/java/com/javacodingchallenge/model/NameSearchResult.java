package com.javacodingchallenge.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javacodingchallenge.View;

import java.util.List;

public class NameSearchResult {

    @JsonView(View.GeneralView.class)
    private final int numChars;

    @JsonView(View.GeneralView.class)
    private final List<Name> names;

    public NameSearchResult(int numChars, List<Name> names) {
        this.numChars = numChars;
        this.names = names;
    }

    public int getNumChars() {
        return numChars;
    }

    public List<Name> getNames() {
        return names;
    }

}
