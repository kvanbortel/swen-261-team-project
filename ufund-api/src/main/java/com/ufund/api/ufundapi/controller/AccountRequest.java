package com.ufund.api.ufundapi.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountRequest {
    @JsonProperty private String name;
    @JsonProperty private String passwordHash;
    
    public AccountRequest(@JsonProperty("name") String name, @JsonProperty("passwordHash") String passwordHash){

        this.name = name;
        this.passwordHash = passwordHash;
    }


    public String getName() { 
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}