package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

@Tag("Model-tier")
public class NeedTest {
    private static final String TEST_ID = "123-456-xyz";
    private static final String TEST_NAME = "Food";
    private static final String TEST_DESCRIPTION = "Provide meals for animals";
    private static final double TEST_DEMAND_RATING = 4.5;
    private static final int TEST_QUANTITY = 100;
    private static final double TEST_COST = 500.0;
    
    private static final boolean DEFAULT_FULFILLMENT_STATUS = false;

    private static final String UPDATED_NAME = "Water";
    private static final String UPDATED_DESCRIPTION = "Provide clean water for animals";
    private static final boolean UPDATED_FULFILLMENT_STATUS = true;
    private static final double UPDATED_DEMAND_RATING = 3.0;
    private static final int UPDATED_QUANTITY = 200;
    private static final double UPDATED_COST = 1000.0;

    private Need need;

    @BeforeEach
    public void setUp() {
        need = new Need(TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_DEMAND_RATING, TEST_QUANTITY, TEST_COST);
    }

    @Test
    public void testConstructor() {
        assertEquals(TEST_ID, need.getId());
        assertEquals(TEST_NAME, need.getName());
        assertEquals(TEST_DESCRIPTION, need.getDescription());
        assertEquals(TEST_DEMAND_RATING, need.getDemandRating());
        assertEquals(TEST_QUANTITY, need.getQuantity());
        assertEquals(TEST_COST, need.getCost());
        assertEquals(DEFAULT_FULFILLMENT_STATUS, need.getFufillmentStatus());
    }

    @Test
    public void testSetName() {
        need.setName(UPDATED_NAME);
        assertEquals(UPDATED_NAME, need.getName());
    }

    @Test
    public void testSetDescription() {
        need.setDescription(UPDATED_DESCRIPTION);
        assertEquals(UPDATED_DESCRIPTION, need.getDescription());
    }

    @Test
    public void testSetFufillmentStatus() {
        need.setFufillmentStatus(UPDATED_FULFILLMENT_STATUS);
        assertTrue(need.getFufillmentStatus());
    }

    @Test
    public void testSetDemandRating() {
        need.setDemandRating(UPDATED_DEMAND_RATING);
        assertEquals(UPDATED_DEMAND_RATING, need.getDemandRating());
    }

    @Test
    public void testSetQuantity() {
        need.setQuantity(UPDATED_QUANTITY);
        assertEquals(UPDATED_QUANTITY, need.getQuantity());
    }

    @Test
    public void testSetCost() {
        need.setCost(UPDATED_COST);
        assertEquals(UPDATED_COST, need.getCost());
    }

}
