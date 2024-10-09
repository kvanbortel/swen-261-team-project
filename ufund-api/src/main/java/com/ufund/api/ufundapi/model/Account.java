package com.ufund.api.ufundapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    @JsonProperty String name;
    @JsonProperty Basket basket;

    public Account(@JsonProperty("name") String name){
        this.name = name;
        this.basket = new Basket();
    }

    public String getName(){
        return this.name;
    }

    public Basket getBasket(){
        return this.basket;
    }

    public String toString(){

        return "http://localhost:8080/accounts/" + getName();
    }
}
