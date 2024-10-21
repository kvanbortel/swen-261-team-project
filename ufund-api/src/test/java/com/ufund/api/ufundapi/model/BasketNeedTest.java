package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class BasketNeedTest {

    //test toString() function
    @Test
    public void testToString() {
        BasketNeed bNeed = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        
        String expected = "{need: {id: id, name: name, description: description, quantity: 0, demandRating: 0.0, cost: 0.0}, quantity: 0}";
        String actual = bNeed.toString();

        assertEquals(expected, actual);
    }

    //test equal when two basketneeds are equal
    @Test
    public void testEqualsTrue() {
        BasketNeed bNeed1 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        BasketNeed bNeed2 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        
        assertEquals(bNeed1, bNeed2);
    }

    //test equal when there are differing needs
    @Test
    public void testEqualDiffNeed() {
        BasketNeed bNeed1 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        BasketNeed bNeed2 = new BasketNeed(
            new Need("DIFFERENT", "name", "description", 0, 0, 0), 0);
        
        assertNotEquals(bNeed1, bNeed2);
    }

    //test equal when there are differing quantities
    @Test
    public void testEqualDiffQuantity() {
        BasketNeed bNeed1 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        BasketNeed bNeed2 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 1);
        
        assertNotEquals(bNeed1, bNeed2);
    }

    //test equal method when other is an object and not a basketneed
    @Test
    public void testEqualWrongType() {
        BasketNeed bNeed1 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);
        
            assertNotEquals(bNeed1, new Object());
    }

    @Test
    public void testGetQuantity() {
        BasketNeed bNeed1 = new BasketNeed(
            new Need("id", "name", "description", 0, 0, 0), 0);

        assertEquals(0, bNeed1.getQuantity());
    }
}
