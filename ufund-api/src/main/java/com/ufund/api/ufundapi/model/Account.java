package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Class representing an account in the model tier
 * 
 * @author Max Klot
 * 
 */
public class Account {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    //serializable name
    @JsonProperty String name;
    //serializable basket
    @JsonProperty Basket basket;

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name a name for the account object
     * @param basket a basket for the account object
     */
    public Account(@JsonProperty("name") String name, @JsonProperty("basket") Basket basket) {
        this.name = name;
        this.basket = basket;
    }

    /**
     * Constructs an account object using a given name
     * @param name a name for the account object 
     */
    public Account(String name){
        this.name = name;
        this.basket = new Basket();
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
     * @return The basket for an accoutn object
     */
    public Basket getBasket(){
        return this.basket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Account){
            Account otherAccount = (Account) other;
            if(this.name.equals(otherAccount.getName()) && this.getBasket().equals(otherAccount.getBasket())){
            return true;
           }
        }
        return false;
    }
}
