package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Class representing an account in the model tier
 * 
 * @author Max Klot
 * 
 */
public class Account implements Comparable<Account> {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    //serializable name
    @JsonProperty String name;
    //serializable basket
    @JsonProperty Basket basket;
    //serializable password hash
    @JsonProperty String passwordHash;

    /// THIS WILL CAUSE A MERGE CONFLICT!!!
    // REMOVE BEFORE MERGE
    // amount spend on funding needs
    @JsonProperty double moneyFunded = 0;
    // number of needs funded
    @JsonProperty int needsFunded = 0;

    /**
     * Add an amount to money funded
     * @param amount
     * @return the new value for this.moneyFunded
     */
    public double addMoneyFunded(double amount) {
        moneyFunded += amount;
        return moneyFunded;
    }

    /**
     * Add a number of needs to needsFunded
     * @param quantity
     * @return the new value for this.needsFunded
     */
    public int addNeedsFunded(int quantity) {
        needsFunded += quantity;
        return needsFunded;
    }

    public double getMoneyFunded() {
        return moneyFunded;
    }

    public int getNeedsFunded() {
        return needsFunded;
    }

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name a name for the account object
     * @param basket a basket for the account object
     */
    public Account(@JsonProperty("name") String name, @JsonProperty("basket") Basket basket, @JsonProperty("passwordHash") String passwordHash) {
        this.name = name;
        this.basket = basket;
        this.passwordHash = passwordHash;
    }

    /**
     * Constructs an account object using a given name
     * @param name a name for the account object 
     */
    public Account(String name, String passwordHash){
        this.name = name;
        this.basket = new Basket();
        this.passwordHash = passwordHash;
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
     * @return The basket for an account object
     */
    public Basket getBasket(){
        return this.basket;
    }

    /**
     * Returns the password hash for an account object
     * @return The password hash for an account object
     */
    public String getPasswordHash(){
        return this.passwordHash;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Account){
            Account otherAccount = (Account) other;
            if(this.name.equals(otherAccount.getName()) && this.getBasket().equals(otherAccount.getBasket()) && this.getPasswordHash().equals(otherAccount.getPasswordHash())){
            return true;
           }
        }
        return false;
    }

    @Override
    public int compareTo(Account other) {
        if (this.getMoneyFunded() > other.getMoneyFunded()) { return  1;}
        if (this.getMoneyFunded() < other.getMoneyFunded()) { return -1;}
        // if they're the same
        return 0;
    }

    @Override
    public String toString() {
        return "Account(" + name + ")";
    }
}
