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
public class Account implements Comparable<Account> {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    // Serializable variables
    @JsonProperty String name;
    @JsonProperty Basket basket;
    //serializable password hash
    @JsonProperty String passwordHash;
    // link to the profile picture
    @JsonProperty String imageLink;

    // amount spend on funding needs
    @JsonProperty double moneyFunded = 0;
    // number of needs funded
    @JsonProperty int needsFunded = 0;
    // last checkout 
    @JsonProperty Instant lastCheckoutInstant;
    // profile info
    @JsonProperty ProfileInfo profileInfo;

    // indicates if a user is god
    @JsonProperty boolean isGod = false;


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

    /**
     * Sets the date for the last time a checkout occurred
     * 
     * @param instant when the checkout occurred
     */
    public void setLastCheckoutInstant(Instant instant) {
        lastCheckoutInstant = instant;
    }

    /**
     * Sets the date for the last time a checkout occurred
     * Uses the current time rather than a parameter
     * 
     * @param instant when the checkout occurred
     */
    public void setLastCheckoutInstant() {
        lastCheckoutInstant = Instant.now();
    }

    /**
     * Gets the last checkout date for this account
     * 
     * @return the Instant object for the time of last checkout
     */
    public Instant getLastCheckoutInstant() {
        return lastCheckoutInstant;
    }

    public double getMoneyFunded() {
        return moneyFunded;
    }

    public int getNeedsFunded() {
        return needsFunded;
    }

    public boolean getIsGod() {
        return isGod;
    }

    public void setIsGod(boolean newIsGod) {
        isGod = newIsGod;
    }

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name the username
     * @param basket the user's basket object
     * @param profileInfo the user's profile information
     * @param lastCheckoutInstant last time the user checked out
     */
    public Account(
            @JsonProperty("name") String name,
            @JsonProperty("basket") Basket basket,
            @JsonProperty("profileInfo") ProfileInfo profileInfo,
            @JsonProperty("passwordHash") String passwordHash ,
            @JsonProperty("imageLink") String imgLink
            ) {
        this.name = name;
        this.basket = basket;
        this.passwordHash = passwordHash;
        this.imageLink = imgLink;
        this.profileInfo = profileInfo;
        if (lastCheckoutInstant == null) {
            this.setLastCheckoutInstant();
        } else {
            this.lastCheckoutInstant = Instant.EPOCH;
        }
    }

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name the username
     * @param basket the user's basket object
     * @param profileInfo the user's profile information
     */
    public Account(
            String name,
            Basket basket,
            ProfileInfo profileInfo,
            String passwordHash
            ) {
        this(name, basket, profileInfo, passwordHash, null);
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
        // set to date that the account was created
        this.lastCheckoutInstant = Instant.now();
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
     * @param imageLink the new image link for a profile picture
     */
    public void setImageLink(String imageLink){
        this.imageLink = imageLink;
    }

    /**
     * Returns the profileInfo for an account object
     * @return the profileInfo for an account object
     */
    public ProfileInfo getProfileInfo() {
        return this.profileInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other){
        if(other instanceof Account){
            Account otherAccount = (Account) other;
            if(this.name.equals(otherAccount.getName())){
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
        // if they're the same again... take whoever reached this money/needs first
        if (this.getLastCheckoutInstant().compareTo(other.getLastCheckoutInstant()) < 0) { return -1; }
        if (this.getLastCheckoutInstant().compareTo(other.getLastCheckoutInstant()) > 0) { return 1; }
        // only possible if they are the same account
        return 0;
    }

    @Override
    public String toString() {
        return "Account(" + name + ")";
    }
}
