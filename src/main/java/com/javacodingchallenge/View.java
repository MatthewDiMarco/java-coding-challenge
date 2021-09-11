package com.javacodingchallenge;

/**
 * Control how data is serialised into JSON
 */
public class View {

    /**
     * Basic viewing: only the id and name are returned
     * Ideal for lists
     */
    public interface GeneralView {}

    /**
     * Includes all fields associated with the 'Name' entity
     * Ideal for single objects
     */
    public interface NameDetailView extends GeneralView {}

}
