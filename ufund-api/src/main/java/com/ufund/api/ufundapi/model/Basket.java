package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tracks the needs in a basket
 * 
 * @author Ryan Richter
 */
public class Basket {
    /** a list of basket needs */
    @JsonProperty ArrayList<BasketNeed> needs;

    /* 
     * Creates a Basket given a list of needs
     * 
     * @param needs list of BasketNeed objects
    */
    public Basket(@JsonProperty("needs") BasketNeed[] needs) {
        this.needs = new ArrayList<BasketNeed>(Arrays.asList(needs));
    }

    public ArrayList<BasketNeed> getNeeds() {
        return needs;
    }
}
