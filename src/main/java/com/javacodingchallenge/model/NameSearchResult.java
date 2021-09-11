package com.javacodingchallenge.model;

import java.util.List;

public class NameSearchResult {

    private final int numChars;
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
