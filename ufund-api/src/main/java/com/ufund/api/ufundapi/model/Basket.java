package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.NameNotFoundException;
import javax.swing.text.html.HTMLDocument;

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
    public Basket(@JsonProperty("needs") List<BasketNeed> needs) {
        this.needs = new ArrayList<>((needs));
    }

    /*
     * Creates a new Basket (for new accounts)
     */
    public Basket() {
        this.needs = new ArrayList<BasketNeed>();
    }

    public ArrayList<BasketNeed> getNeeds() {
        return this.needs;
    }

    /**
     * Gets a BasketNeeds index in this.needs
     * @param need
     * @return int index, -1 if not found
     */    
    private int getBasketNeedIndex(Need need) {
        for (int i=0 ; i<needs.size() ; i++) {
            // current need
            BasketNeed curr = needs.get(i);
            if (curr.need.equals(need)) {
                return i;
            }
        }
        // if the need is not found return null
        return -1;
    }

    /**
     * Gets a BasketNeeds by id in this.needs
     * @param id
     * @return BasketNeed need, null if not found
     */    
    private BasketNeed getBasketNeedById(String id) {
        for (int i=0 ; i<needs.size() ; i++) {
            // current need
            BasketNeed curr = needs.get(i);
            if (curr.need.id.equals(id)) {
                return curr;
            }
        }
        // if the need is not found return null
        return null;
    }

    /**
     * Gets a BasketNeed given its need if it is in the basket, otherwise returns null
     * @param need
     * @return BasketNeed if it is found, otherwise null
     */
    public BasketNeed getBasketNeed(Need need) { 
        int index = getBasketNeedIndex(need);

        // if the need is not found, return null
        if (index == -1) {
            return null;
        }

        // otherwise return the need that we found
        return needs.get(index);
    }

    /**
     * Determines if the basket contains a Need
     * 
     * @param need need to search for
     * 
     * @return whether or not the need is found
     */
    public boolean hasNeed(Need need) { 
        return getBasketNeedIndex(need) != -1; 
    }

    /**
     * Set a BasketNeed's need to a given need
     * @param need
     * @return new BasketNeed if it is updated successfully, otherwise null
     */
    public BasketNeed setNeed(Need need) { 
        BasketNeed bNeed = getBasketNeedById(need.getId());

        // if the need is not found, return null
        if (bNeed == null) {
            return null;
        }

        // if the need is found, remove that need and replace it with the new one (with the old quantity)
        BasketNeed newBNeed = new BasketNeed(need, bNeed.quantity);

        updateNeed(need, -bNeed.quantity); // remove the need
        updateNeed(newBNeed.getNeed(), newBNeed.getQuantity()); // add the new one

        // reverse sort the needs by demand
        this.needs.sort((n2, n1) -> Double.compare(n1.getNeed().getDemandRating(), n2.getNeed().getDemandRating()));

        // return the newly updated need
        return newBNeed;
    }

    /**
     * Adds or subtracts a need quantity from the basket. If that need is not in the basket, 
     * add it to the basket, if it would drop below 0, remove it from the basket
     * 
     * @param need the need to update in the basket
     * @param amount the amount to change the need by (positive or negative)
     */
    public void updateNeed(Need need, int amount) {

        BasketNeed basketNeed = getBasketNeed(need);

        if (basketNeed == null) {
            BasketNeed newBasketNeed = new BasketNeed(need, amount);
            this.needs.add(newBasketNeed);
            
            // reverse sort the needs by demand
            this.needs.sort((n2, n1) -> Double.compare(n1.getNeed().getDemandRating(), n2.getNeed().getDemandRating()));
        } else {
            basketNeed.quantity += amount;
            if(basketNeed.quantity <= 0){
                needs.remove(basketNeed);
            }
        }
    }

    /**
     * Adds a need to the basket. If that need has already been added, 
     * increases the quantity by 1
     * 
     * @param need the need to add to the basket
     */
    public void addNeed(Need need) {
        BasketNeed basketNeed = getBasketNeed(need);

        if (basketNeed == null) {
            basketNeed = new BasketNeed(need, 1);
            this.needs.add(basketNeed);

            // reverse sort the needs by demand
            this.needs.sort((n2, n1) -> Double.compare(n1.getNeed().getDemandRating(), n2.getNeed().getDemandRating()));
        } else {
            basketNeed.quantity += 1;
        }
    }

    /**
     * Removes a need form the basket. If the quantity of that need is 
     * more than 1, just decrements the quantity field
     * 
     * @param need the need to remove from the basket
     */
    public void removeNeed(Need need) {
        int index = getBasketNeedIndex(need);

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Basket){
            Basket otherBasket = (Basket) other;
            if(this.getNeeds().isEmpty() && otherBasket.getNeeds().isEmpty()){return true;}
            for( BasketNeed need : this.getNeeds()){
                if(!otherBasket.hasNeed(need.getNeed())){
                    return false;
                }
            }
            for( BasketNeed need : otherBasket.getNeeds()){
                if(!this.hasNeed(need.getNeed())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
