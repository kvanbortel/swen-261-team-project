package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileInfo {

    // Serializable variables
    @JsonProperty String password;
    @JsonProperty String profilePic;
    @JsonProperty String pronouns;
    @JsonProperty String alias;
    @JsonProperty String bio;
    @JsonProperty Region region;
    @JsonProperty String phoneNumber;
    @JsonProperty String email;
    @JsonProperty String ssn;

    /**
     * Constructs a ProfileInfo object using a given name and a given Basket
     * @param password the user's password
     * @param profilePic a link to the user's profile picture
     * @param pronouns the user's pronouns
     * @param alias the user's nickname to appear on the leaderboard
     * @param bio the user's bio
     * @param region the region of New York State the user is from
     * @param phoneNumber the user's phone number
     * @param email the user's email address
     * @param ssn the user's social security number
     */
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
        this.password = password;
        this.profilePic = profilePic;
        this.pronouns = pronouns;
        this.alias = alias;
        this.bio = bio;
        this.region = region;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ssn = ssn;
    }

    /**
     * Returns the user's password
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the user's profile picture
     * @return the user's profile picture
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * Return the user's pronouns
     * @return the user's pronouns
     */
    public String getPronouns() {
        return pronouns;
    }

    /**
     * Return the user's nickname for the leaderboard
     * @return the user's alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Return the user's bio
     * @return the user's bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Return the region of New York State the user is from
     * @return the user's region
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Return the user's phone number
     * @return the user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Return the user's email address
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Return the user's social security number
     * @return the user's social security number
     */
    public String getSsn() {
        return ssn;
    }
}
