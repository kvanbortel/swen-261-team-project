package com.ufund.api.ufundapi.model;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Basket {
    @JsonProperty
    private ArrayList<BasketNeed> donationNeeds;
    @JsonProperty
    private ArrayList<BasketNeed> adoptionNeeds;
    @JsonProperty
    private ArrayList<BasketNeed> volunteerNeeds;

    public Basket(){

        donationNeeds = new ArrayList<>();
        adoptionNeeds = new ArrayList<>();
        volunteerNeeds = new ArrayList<>();
    }
}
