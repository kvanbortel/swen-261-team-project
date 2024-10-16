package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.NeedDAO;

/**
 * Test the Basket class
 * 
 * @author Ryan Richter
 */
@Tag("Model-tier")
public class BasketTest {

    private Basket createBasket() {
        BasketNeed[] needslist = {
            new BasketNeed("id0", 3),
            new BasketNeed("id1", 2),
            new BasketNeed("id2", 1)
        };
        ArrayList<BasketNeed> needs = new ArrayList<BasketNeed>(Arrays.asList(needslist));
        Basket basket = new Basket(needs);
        return basket;
    }

    @Test
    public void testSerializeBasket() {}

    @Test
    public void testDeserializeBasket() {}

    @Test
    public void testGetBasketNeedsEmpty() {
        BasketNeed[] needs = {};
        Basket basket = new Basket(Arrays.asList(needs));

        ArrayList<BasketNeed> needsList1 = new ArrayList<>();
        ArrayList<BasketNeed> needsList2 = basket.getNeeds();

        assertEquals(needsList1, needsList2);
    }

    @Test
    public void testGetBasketNeedsHasNeeds() {
        Basket basket = createBasket();

        ArrayList<BasketNeed> needsList1 = new ArrayList<>();
        ArrayList<BasketNeed> needsList2 = basket.getNeeds();
        needsList1.add(new BasketNeed("id0", 3));
        needsList1.add(new BasketNeed("id1", 2));
        needsList1.add(new BasketNeed("id2", 1));

        assertEquals(needsList1, needsList2);
    }

    @Test
    public void testGetBasketNeed0() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id0", 3);
        BasketNeed actual = basket.getBasketNeed("id0");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeed1() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id1", 2);
        BasketNeed actual = basket.getBasketNeed("id1");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeed2() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id2", 1);
        BasketNeed actual = basket.getBasketNeed("id2");

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeedNotFound() {
        Basket basket = createBasket();
        BasketNeed expected = null;
        BasketNeed actual = basket.getBasketNeed("NON-EXISTENT-ID");

        assertEquals(expected, actual);
    }

    @Test
    public void testHasNeedTrue() {
        Basket basket = createBasket();

        boolean actual = basket.hasNeed("id2");
        
        assertTrue(actual);
    }

    @Test
    public void testHasNeedFalse() {
        Basket basket = createBasket();

        boolean actual = basket.hasNeed("NON-EXISTENT-ID");
        
        assertFalse(actual);
    }

    @Test
    public void testAdd3Needs() {
        Basket basket = new Basket(); // empty

        basket.addNeed("id0");
        basket.addNeed("id1");
        basket.addNeed("id2");

        ArrayList<BasketNeed> expected = new ArrayList<>();
        expected.add(new BasketNeed("id0", 1));
        expected.add(new BasketNeed("id1", 1));
        expected.add(new BasketNeed("id2", 1));

        ArrayList<BasketNeed> actual = basket.getNeeds();

        assertEquals(expected, actual);
    }

    @Test
    public void testAddNeedTwice() {
        Basket basket = new Basket();

        basket.addNeed("id0");
        basket.addNeed("id0");

        BasketNeed expected = new BasketNeed("id0", 2);
        BasketNeed actual = basket.getBasketNeed("id0");

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveNeedNonOneQuantityExists() {
        Basket basket = createBasket();

        basket.removeNeed("id0");
        basket.removeNeed("id0");

        BasketNeed expected = new BasketNeed("id0", 1);
        BasketNeed actual = basket.getBasketNeed("id0");

        assertEquals(expected, actual);
    }

    @Test 
    public void testRemoveNeedOneExists() {
        Basket basket = createBasket();

        basket.removeNeed("id2");

        BasketNeed actual = basket.getBasketNeed("id2");

        assertNull(actual);
    }

    @Test 
    public void testRemoveNonExistentNeed() {
        Basket basket = createBasket();

        ArrayList<BasketNeed> before = basket.getNeeds();

        basket.removeNeed("id4");

        ArrayList<BasketNeed> after = basket.getNeeds();

        assertEquals(before, after);
    }
}
