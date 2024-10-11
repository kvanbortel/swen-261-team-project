package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Need {
    // use the same logger as the HERO example for message handling?
    private static final Logger LOG = Logger.getLogger(Need.class.getName());

    /**
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of JSON objects to Java objects in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    @JsonProperty String id; // UUID identifer for the need
    @JsonProperty String name; // Name of the need
    @JsonProperty String description; // Description of the need
    @JsonProperty boolean fulfillmentStatus; // Whether or not the need is fufilled
    @JsonProperty double demandRating; // Demand of the need
    @JsonProperty double cost; // Cost of the need
    @JsonProperty int quantity;


    /**
     * Create a need object with the given properties
     * @param id The id of the need - a unique indentifer string
     * @param name The name of the need
     * @param description A text description of the need
     * @param demandRating A double describing the demand for the need
     * @param quantity The positive int number of this need
     * @param cost The monetary cost for this need
     */
    public Need(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("demandRating") double demandRating, @JsonProperty("quantity") int quantity, @JsonProperty("cost") double cost){
        this.id = id;
        this.name = name;
        this.description = description;
        this.fulfillmentStatus = false;
        this.demandRating = demandRating;
        this.quantity = quantity;
        this.cost = cost;
    }

    /**
     * Get the id of the need
     * @return the unique id of the need
    */
    public String getId(){
        return this.id;
    }

    /**
     * Get the name of the need
     * @return the name of the need
    */
    public String getName(){
        return this.name;
    }

    /**
     * Set the name of the need
     * @param text the new name for the need
    */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Get the description of the need
     * @return the text description of the need
    */
    public String getDescription(){
        return this.description;
    }

    /**
     * Set the description of the need
     * @param text the new description of the need
    */
    public void setDescription(String text){
        this.description = text;
    }

    /**
     * Get the fufillment status of the need
     * @return the fufillment status of the need
    */
    public boolean getFulfillmentStatus(){
        return this.fulfillmentStatus;
    }

    /**
     * Set the fufillment status of the need
     * @param status the new fufillment status of the need
    */
    public void setFulfillmentStatus(boolean status){
        this.fulfillmentStatus = status;
    }

    /**
     * Get the demand rating of the need
     * @return the demand rating of the need
    */
    public double getDemandRating(){
        return this.demandRating;
    }

    /**
     * Set the demand rating of the need
     * @param rating the new demand rating of the need
    */
    public void setDemandRating(double rating){
        this.demandRating = rating;
    }

    /**
     * Get the quantity of the need
     * @return the quantity of the need
    */
    public int getQuantity(){
        return this.quantity;
    }

    /**
     * Set the quantity of the need
     * @param quantity the new quantity of the need
    */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * Get the cost of the need
     * @return the cost of the need
    */
    public double getCost(){
        return this.cost;
    }

    /**
     * Set the cost of the need
     * @param cost the new cost of the need
    */
    public void setCost(double cost){
        this.cost = cost;
    }

}
