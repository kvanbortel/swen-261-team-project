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
    @JsonProperty boolean isGod;
    @JsonProperty boolean isPublic;
    @JsonProperty int moneyFunded;
    @JsonProperty int needsFunded;
    @JsonProperty Instant lastDonated;
    @JsonProperty int rank;
    @JsonProperty Level level;

    /**
     * Constructs an account object using a given name and a given Basket
     * @param name the username
     * @param basket the user's basket object
     * @param profileInfo the user's profile information
     * @param isGod whether the user has god status
     * @param isPublic whether the account is visible for searches
     * @param moneyFunded the total amount of money user funded
     * @param needsFunded the total amount of Needs user funded
     * @param lastDonated the time the user last donated
     * @param rank the user's numerical position on the leaderboard
     * @param level the user's level
     */
    public Account(
            @JsonProperty("name") String name,
            @JsonProperty("basket") Basket basket,
            @JsonProperty("profileInfo") ProfileInfo profileInfo,
            @JsonProperty("isGod") boolean isGod,
            @JsonProperty("isPublic") boolean isPublic,
            @JsonProperty("moneyFunded") int moneyFunded,
            @JsonProperty("needsFunded") int needsFunded,
            @JsonProperty("lastDonated") Instant lastDonated,
            @JsonProperty("rank") int rank,
            @JsonProperty("level") Level level
            ) {
        this.name = name;
        this.basket = basket;
        this.profileInfo = profileInfo;
        this.isGod = isGod;
        this.isPublic = isPublic;
        this.moneyFunded = moneyFunded;
        this.needsFunded = needsFunded;
        this.lastDonated = lastDonated;
        this.rank = rank;
        this.level = level;
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
     * Returns the isGod status for an account object
     * @return the isGod status for an account object
     */
    public boolean isGod() { return isGod; }

    /**
     * Returns the isPublic status for an account object
     * @return the isPublic status for an account object
     */
    public boolean isPublic() { return isPublic; }

    /**
     * Returns the amount of money a user funded
     * @return the amount of money a user funded
     */
    public int getMoneyFunded() { return moneyFunded; }

    /**
     * Returns the amount of needs a user funded
     * @return the amount of needs a user funded
     */
    public int getNeedsFunded() { return needsFunded; }

    /**
     * Returns the timestamp a user last donated
     * @return the timestamp a user last donated
     */
    public Instant getLastDonated() { return lastDonated; }

    /**
     * Returns the rank of a user
     * @return the rank of a user
     */
    public int getRank() { return rank; }

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
