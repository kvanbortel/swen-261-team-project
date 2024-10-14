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
    public Basket(@JsonProperty("basket") BasketNeed[] needs) {
        this.needs = new ArrayList<BasketNeed>(Arrays.asList(needs));
    }

    /*
     * Creates a new Basket (for new accounts)
     */
    public Basket() {
        this.needs = new ArrayList<BasketNeed>();
    }

    public ArrayList<BasketNeed> getNeeds() {
        return needs;
    }

    // stubbing methods (remove this comment when all methods are implemented)


    /**
     * Gets a BasketNeed given its id if it is in the basket, otherwise returns null
     * @param needId
     * @return
     */
    public BasketNeed getBasketNeed(String needId) { return new BasketNeed("FAKEID", 0); }

    /**
     * Determines if the basket contains a needId
     * 
     * @param needId needId to search for
     * 
     * @return whether or not the needId is found
     */
    public boolean hasNeed(String needId) { return false; }

    /**
     * Adds a need to the basket. If that need has already been added, 
     * increases the quantity by 1
     * 
     * @param needId the needId to add to the basket
     */
    public void addNeed(String needId) {}

    /**
     * Removes a need form the basket. If the quantity of that need is 
     * more than 1, just decrements the quantity field
     * 
     * @param needId the neewdId to remove from the basket
     */
    public void removeNeed(String needId) {}
}
