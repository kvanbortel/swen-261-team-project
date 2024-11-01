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
    // link to the profile picture
    @JsonProperty String imageLink;

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
        this.imageLink = "http://res.cloudinary.com/dc5ifh1f7/image/upload/v1730683024/a.png"; // default profile picture 
    }

    /**
     * Constructs an account object using a given name
     * @param name a name for the account object 
     */
    public Account(String name, String passwordHash){
        this.name = name;
        this.basket = new Basket();
        this.passwordHash = passwordHash;
        this.imageLink = "http://res.cloudinary.com/dc5ifh1f7/image/upload/v1730683024/a.png"; // default profile picture 
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
     * Set the profile picture of an account object
     * @param imageL the new image link for a profile picture
     */
    public void setImageLink(String imageLink){
        this.imageLink = imageLink;
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
    /**
     * Compares the RANK of 2 users
     * -1 if the rank of this is less than rank of other
     * 1 if the rank of this is more than rank of other
     */
    public int compareTo(Account other) {
        if (this.getMoneyFunded() > other.getMoneyFunded()) { return  -1;}
        if (this.getMoneyFunded() < other.getMoneyFunded()) { return 1;}
        // if they're the same... (tie-breaker 1)
        if (this.getNeedsFunded() > other.getNeedsFunded()) { return -1; }
        if (this.getNeedsFunded() < other.getNeedsFunded()) { return 1; }
        // if they're the same again... take them in alpha order
        if (this.getName().compareTo(other.getName()) < 0) { return -1; }
        if (this.getName().compareTo(other.getName()) > 0) { return 1; }
        // only possible if they are the same account
        return 0;
    }

    @Override
    public String toString() {
        return "Account(" + name + ")";
    }
}
