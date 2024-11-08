package com.ufund.api.ufundapi.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class for testing the ProfileInfo class
 *
 * @author Jonah Witte
 */
@Tag("Model-tier")
public class AdminInfoTest {

    private static final int TEST_USER_NUMBER = 100;
    private static final int TEST_NEEDS_FUNDED = 50;
    private static final double TEST_MONEY_FUNDED = 20000.10;
    private static final Instant TEST_LAST_FUNDED_INSTANT = Instant.now();
    private static final Map<Region, Integer> TEST_REGIONS = Map.of(
        Region.CAPITAL_DISTRICT, 30,
        Region.NEW_YORK_CITY, 70
    );
    private static final Map<Region, Double> TEST_FUNDED_BY_REGION = Map.of(
        Region.CAPITAL_DISTRICT, 15000.0,
        Region.NEW_YORK_CITY, 5000.0
    );

    private AdminInfo adminInfo;
    private AdminInfo adminInfoDiffUserNumber;
    private AdminInfo adminInfoDiffNeedsFunded;
    private AdminInfo adminInfoDiffMoneyFunded;
    private AdminInfo adminInfoDiffInstant;
    private AdminInfo adminInfoDiffRegions;
    private AdminInfo adminInfoDiffFundedByRegion;
    
    @BeforeEach
    public void setUp() {
        adminInfo = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        adminInfoDiffUserNumber = new AdminInfo(
            200, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        adminInfoDiffNeedsFunded = new AdminInfo(
            TEST_USER_NUMBER, 60, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        adminInfoDiffMoneyFunded = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, 25000,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        adminInfoDiffInstant = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            Instant.MAX, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        adminInfoDiffRegions = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, Map.of(Region.NEW_YORK_CITY, 50),
            TEST_FUNDED_BY_REGION
        );
        adminInfoDiffFundedByRegion = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS,
            Map.of(Region.CAPITAL_DISTRICT, 10000.0)
        );
    }

    // Test for constructing an AdminInfo
    @Test
    public void testCreateAdminInfo() {
        AdminInfo newAdminInfo = new AdminInfo(TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED, TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION);
        assertEquals(TEST_USER_NUMBER, newAdminInfo.userNumber);
        assertEquals(TEST_NEEDS_FUNDED, newAdminInfo.needsFunded);
        assertEquals(TEST_MONEY_FUNDED, newAdminInfo.moneyFunded);
        assertEquals(TEST_LAST_FUNDED_INSTANT, newAdminInfo.lastFundedInstant);
        assertEquals(TEST_REGIONS, newAdminInfo.regions);
        assertEquals(TEST_FUNDED_BY_REGION, newAdminInfo.fundedByRegion);
    }

    // Test for constructing an AdminInfo with nulls
    @Test
    public void testCreateEmptyAdminInfo() {
        AdminInfo newAdminInfo = new AdminInfo(0, 0, 0, null, null, null);
        assertEquals(0, newAdminInfo.userNumber);
        assertEquals(0, newAdminInfo.needsFunded);
        assertEquals(0, newAdminInfo.moneyFunded);
        assertNull(newAdminInfo.lastFundedInstant);
        assertNull(newAdminInfo.regions);
        assertNull(newAdminInfo.fundedByRegion);
    }

    // Test for comparing indentical objects
    @Test
    public void testEquals_EqualObjects() {
        AdminInfo identicalAdminInfo = new AdminInfo(
            TEST_USER_NUMBER, TEST_NEEDS_FUNDED, TEST_MONEY_FUNDED,
            TEST_LAST_FUNDED_INSTANT, TEST_REGIONS, TEST_FUNDED_BY_REGION
        );
        assertTrue(adminInfo.equals(identicalAdminInfo));
    }

    // Test equality for the same object
    @Test
    public void testEquals_SameObject() {
        assertTrue(adminInfo.equals(adminInfo));
    }

    // Test equality with null object
    @Test
    public void testEquals_NullObject() {
        assertFalse(adminInfo.equals(null));
    }

    // Test equality with different class
    @Test
    public void testEquals_DifferentClass() {
        String otherObject = "HelloWorld";
        assertFalse(adminInfo.equals(otherObject));
    }

    // Test inequality with different userNumber
    @Test
    public void testEquals_DiffUserNumber() {
        assertFalse(adminInfo.equals(adminInfoDiffUserNumber));
    }

    // Test inequality with different needsFunded
    @Test
    public void testEquals_DiffNeedsFunded() {
        assertFalse(adminInfo.equals(adminInfoDiffNeedsFunded));
    }

    // Test inequality with different moneyFunded
    @Test
    public void testEquals_DiffMoneyFunded() {
        assertFalse(adminInfo.equals(adminInfoDiffMoneyFunded));
    }

    // Test inequality with different instant funded
    @Test
    public void testEquals_DiffInstantFunded() {
        assertFalse(adminInfo.equals(adminInfoDiffInstant));
    }

    // Test inequality with different regions
    @Test
    public void testEquals_DiffRegions() {
        assertFalse(adminInfo.equals(adminInfoDiffRegions));
    }

    // Test inequality with different fundedByRegion
    @Test
    public void testEquals_DiffFundedByRegion() {
        assertFalse(adminInfo.equals(adminInfoDiffFundedByRegion));
    }

}
