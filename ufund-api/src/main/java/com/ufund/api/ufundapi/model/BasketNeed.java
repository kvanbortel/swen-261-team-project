package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Just a container for Needs that are stored in the basket.
 * 
 * @author Ryan Richter
 */
public class BasketNeed {
    /** The need stored in the basket. Need is a reference
     * So it must be immutable (therefore final) */
    @JsonProperty final Need need;
    /** quantity of the need that is being tracked */
    @JsonProperty int quantity;

    /**
     * Creates a BasketNeed with the given properties. 
     * 
     * @param need The actual need object. 
     * @param quantity The quantity of this need that is in the basket. 
     */
    public BasketNeed(@JsonProperty("need") Need need, @JsonProperty("quantity") int quantity) {
        this.need = need;
        this.quantity = quantity;
    }

    /**
     * Determines if 2 BasketNeeds are equal
     * 
     * 2 basket needs are equal if BOTH their need and quantity are the same. 
     */
    public boolean equals(Object obj) {
        if (obj instanceof BasketNeed) {
            BasketNeed o = (BasketNeed)obj;
            return (this.need.equals(o.need) && this.quantity == o.quantity);
        }
        return false;
    }

    /**
     * Represents a BasketNeed as a string
     */
    public String toString() {
        return "{need: " + need.toString() + ", quantity: " + quantity + "}";
    }
}
