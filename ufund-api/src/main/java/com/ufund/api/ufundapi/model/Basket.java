package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.NameNotFoundException;

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

    /*
     * Creates a new Basket (for new accounts)
     */
    public Basket() {
        this.needs = new ArrayList<BasketNeed>();
    }

    public ArrayList<BasketNeed> getNeeds() {
        return needs;
    }

    /**
     * Gets a BasketNeeds index in this.needs
     * @param needId
     * @return int index, -1 if not found
     */    
    private int getBasketNeedIndex(String needId) {
        for (int i=0 ; i<needs.size() ; i++) {
            // current need
            BasketNeed curr = needs.get(i);
            if (curr.id.equals(needId)) {
                return i;
            }
        }
        // if the need is not found return null
        return -1;
    }

    /**
     * Gets a BasketNeed given its id if it is in the basket, otherwise returns null
     * @param needId
     * @return BasketNeed if it is found, otherwise null
     */
    public BasketNeed getBasketNeed(String needId) { 
        int index = getBasketNeedIndex(needId);

        // if the need is not found, return null
        if (index == -1) {
            return null;
        }

        // otherwise return the need that we found
        return needs.get(index);
    }

    /**
     * Determines if the basket contains a needId
     * 
     * @param needId needId to search for
     * 
     * @return whether or not the needId is found
     */
    public boolean hasNeed(String needId) { 
        return getBasketNeedIndex(needId) != -1; 
    }

    /**
     * Adds a need to the basket. If that need has already been added, 
     * increases the quantity by 1
     * 
     * @param needId the needId to add to the basket
     */
    public void addNeed(String needId) {
        BasketNeed basketNeed = getBasketNeed(needId);

        if (basketNeed == null) {
            basketNeed = new BasketNeed(needId, 1);
            this.needs.add(basketNeed);
        } else {
            basketNeed.quantity += 1;
        }
    }

    /**
     * Removes a need form the basket. If the quantity of that need is 
     * more than 1, just decrements the quantity field
     * 
     * @param needId the neewdId to remove from the basket
     */
    public void removeNeed(String needId) {
        int index = getBasketNeedIndex(needId);

        if (index == -1) {
            // the need already isn't here so no need to remove it
            return;
        } 

        BasketNeed basketNeed = needs.get(index);
        if (basketNeed.quantity > 1) {
            // decrement the quantity
            basketNeed.quantity -= 1;
        } else {
            needs.remove(index);
        }
    }
}
