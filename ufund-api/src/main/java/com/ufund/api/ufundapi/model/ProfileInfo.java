package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileInfo {
    // Profile info
    @JsonProperty String password;
    @JsonProperty String profilePic;
    @JsonProperty String pronouns;
    @JsonProperty String alias; // user's nickname to appear on leaderboard
    @JsonProperty String bio;
    @JsonProperty Region region; // region of New York State
    @JsonProperty String phoneNumber;
    @JsonProperty String email; // email address
    @JsonProperty String ssn; // social security number

    public ProfileInfo(
            @JsonProperty("password") String password,
            @JsonProperty("profilePic") String profilePic,
            @JsonProperty("pronouns") String pronouns,
            @JsonProperty("alias") String alias,
            @JsonProperty("bio") String bio,
            @JsonProperty("region") Region region,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("email") String email,
            @JsonProperty("ssn") String ssn
            ) {
        
    }
}
