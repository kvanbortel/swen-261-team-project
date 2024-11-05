package com.ufund.api.ufundapi.model;

import java.time.Instant;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Class representing an account in the model tier
 * 
 * @author Max Klot, Kayla Van Bortel
 * 
 */
public class Account {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    // Serializable variables
    @JsonProperty String name;
    @JsonProperty Basket basket;
    @JsonProperty ProfileInfo profileInfo;
    @JsonProperty Level level;

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name the username
     * @param basket the user's basket object
     * @param profileInfo the user's profile information
     * @param level the user's level
     */
    public Account(
            @JsonProperty("name") String name,
            @JsonProperty("basket") Basket basket,
            @JsonProperty("profileInfo") ProfileInfo profileInfo,
            @JsonProperty("level") Level level
            ) {
        this.name = name;
        this.basket = basket;
        this.profileInfo = profileInfo;
        this.level = level;
    }

    /**
     * Constructs an account object using a given name
     * @param name a name for the account object 
     */
    public Account(String name){
        this.name = name;
        this.basket = new Basket();
        this.profileInfo = new ProfileInfo();
    }

    /**
     * Returns the name for an account object
     * @return the name for an account object
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the basket for an account object
     * @return the basket for an account object
     */
    public Basket getBasket(){
        return this.basket;
    }

    /**
     * Returns the profileInfo for an account object
     * @return the profileInfo for an account object
     */
    public ProfileInfo getProfileInfo() {
        return this.profileInfo;
    }

    /**
     * Returns the level of the user
     * @return the user's level (noob, pro, master, champion)
     */
    public Level getLevel() { return level; }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Account){
            Account otherAccount = (Account) other;
            if (this.name.equals(otherAccount.getName()) && this.getBasket().equals(otherAccount.getBasket())){
            return true;
           }
        }
        return false;
    }
}
