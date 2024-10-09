package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private static final Logger LOG = Logger.getLogger(Account.class.getName());

    @JsonProperty String name;
    @JsonProperty ArrayList<Need> needs;

    public Account(@JsonProperty("name") String name){
        this.name = name;
        this.needs = new ArrayList<Need>();
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<Need> getNeeds(){
        return this.needs;
    }

    public String toString(){

        return "http://localhost:8080/accounts/" + getName();
    }
}
