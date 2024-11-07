package com.ufund.api.ufundapi.model;

import java.util.Map;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminInfo {
    // these are json properties even though it's not stored on the backend because it should be able to be turned into a json object when passed to the frontend
    @JsonProperty int userNumber;
    @JsonProperty int needsFunded;
    @JsonProperty int moneyFunded;
    @JsonProperty Instant lastFundedInstant;
    @JsonProperty Map<Region, Integer> regions;
    @JsonProperty Map<Region, Double> fundedByRegion;

    /**
     * Constructs a AdminInfo object using user funding and region data
     * @param userNumber number of users
     * @param needsFunded total number of the number of needs funded
     * @param moneyFunded the total amount of money funded
     * @param lastFundedInstant the most recent funding instant
     * @param regions a map of regions and associated number of users
     * @param fundedByRegion a map indicating the amount funded by region
     */
    public AdminInfo(
        @JsonProperty("userNumber") int userNumber,
        @JsonProperty("needsFunded") int needsFunded,
        @JsonProperty("moneyFunded") int moneyFunded,
        @JsonProperty("lastFundedInstant") Instant lastFundedInstant,
        @JsonProperty("regions") Map<Region, Integer> regions,
        @JsonProperty("fundedByRegion") Map<Region, Double> fundedByRegion
        ) {
        this.userNumber = userNumber;
        this.needsFunded = needsFunded;
        this.moneyFunded = moneyFunded;
        this.lastFundedInstant = lastFundedInstant;
        this.regions = regions;
        this.fundedByRegion = fundedByRegion;
    }
}
