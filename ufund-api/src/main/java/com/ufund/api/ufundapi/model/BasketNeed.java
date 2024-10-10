package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Just a container for Needs that are stored in the basket.
 * 
 * @author Ryan Richter
 */
public class BasketNeed {
    /** id of the need that the BasketNeed is tracking */
    @JsonProperty String id;
    /** quantity of the need that is being tracked */
    @JsonProperty int quantity;

    /**
     * Creates a BasketNeed with the given properties. 
     * 
     * @param id The id of the Need. 
     * @param quantity The quantity of this need that is in the basket. 
     */
    public BasketNeed(@JsonProperty("id") String id, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    /**
     * Determines if 2 BasketNeeds are equal
     * 
     * 2 basket needs are equal if BOTH their id and quantity are the same. 
     */
    public boolean equals(Object obj) {
        if (obj instanceof BasketNeed) {
            BasketNeed o = (BasketNeed)obj;
            return (this.id == o.id && this.quantity == o.quantity);
        }
        return false;
    }
}
