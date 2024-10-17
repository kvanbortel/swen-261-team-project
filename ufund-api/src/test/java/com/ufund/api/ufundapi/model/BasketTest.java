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
import java.util.List;
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

    private List<BasketNeed> getTestNeeds() {
        BasketNeed[] needs = {
            new BasketNeed(new Need("id0", "name0", "descr0", 0, 12, 10), 3),
            new BasketNeed(new Need("id1", "name1", "descr1", 1, 19, 15), 2),
            new BasketNeed(new Need("id2", "name2", "descr2", 2, 6, 6), 1),
        };
        return Arrays.asList(needs);
    }

    private Basket createTestBasket() {
        // not a test...
        // used to create test baskets

        List<BasketNeed> needs = getTestNeeds();
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

        List<BasketNeed> needsList1 = new ArrayList<>();
        List<BasketNeed> needsList2 = basket.getNeeds();
        assertEquals(needsList1, needsList2);
    }

    @Test
    public void testGetBasketNeedsHasNeeds() {
        Basket basket = createTestBasket();

        List<BasketNeed> needsList1 = getTestNeeds();
        List<BasketNeed> needsList2 = basket.getNeeds();

        assertEquals(needsList1, needsList2);
    }

    @Test
    public void testGetBasketNeed0() {
        Basket basket = createTestBasket();
        Need need = new Need("id0", "name0", "descr0", 0, 0, 0);
        BasketNeed expected = new BasketNeed(need, 3);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeed1() {
        Basket basket = createTestBasket();
        Need need = new Need("id1", "name1", "descr1", 1, 1, 1);
        BasketNeed expected = new BasketNeed(need, 2);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeed2() {
        Basket basket = createTestBasket();
        Need need = new Need("id2", "name2", "descr2", 2, 2, 2);
        BasketNeed expected = new BasketNeed(need, 1);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetBasketNeedNotFound() {
        Basket basket = createTestBasket();
        BasketNeed expected = null;
        // null will not be found in the basket
        BasketNeed actual = basket.getBasketNeed(null);

        assertEquals(expected, actual);
    }

    @Test
    public void testHasNeedTrue() {
        Basket basket = createTestBasket();
        Need need = basket.getNeeds().get(2).need;

        boolean actual = basket.hasNeed(need);
        
        assertTrue(actual);
    }

    @Test
    public void testHasNeedFalse() {
        Basket basket = createTestBasket();

        boolean actual = basket.hasNeed(null);
        
        assertFalse(actual);
    }

    @Test
    public void testAdd3Needs() {
        Basket basket = new Basket(); // empty
        List<BasketNeed> needs = getTestNeeds();

        basket.addNeed(needs.get(0).need);
        basket.addNeed(needs.get(0).need);
        basket.addNeed(needs.get(0).need);
        basket.addNeed(needs.get(1).need);
        basket.addNeed(needs.get(1).need);
        basket.addNeed(needs.get(2).need);

        List<BasketNeed> actual = basket.getNeeds();

        assertEquals(needs, actual);
    }

    @Test
    public void testAddNeedTwice() {
        Basket basket = new Basket();

        Need need = new Need("id0", "name0", "descr0", 0, 1, 2);

        basket.addNeed(need);
        basket.addNeed(need);

        BasketNeed expected = new BasketNeed(need, 2);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveNeedNonOneQuantityExists() {
        Basket basket = createTestBasket();
        Need need = basket.getNeeds().get(0).need;

        basket.removeNeed(need);
        basket.removeNeed(need);

        BasketNeed expected = new BasketNeed(need, 1);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    @Test 
    public void testRemoveNeedOneExists() {
        Basket basket = createTestBasket();
        Need need = basket.getNeeds().get(2).need;

        basket.removeNeed(need);

        BasketNeed actual = basket.getBasketNeed(need);

        assertNull(actual);
    }

    @Test 
    public void testRemoveNonExistentNeed() {
        Basket basket = createTestBasket();

        ArrayList<BasketNeed> before = basket.getNeeds();

        basket.removeNeed(null);

        ArrayList<BasketNeed> after = basket.getNeeds();

        assertEquals(before, after);
    }
}
