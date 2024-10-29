package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Basket class
 * 
 * @author Ryan Richter
 */
@Tag("Model-tier")
public class BasketTest {

    //list of basketneeds used for testing 
    private List<BasketNeed> getTestNeeds() {
        BasketNeed[] needs = {
            new BasketNeed(new Need("id0", "name0", "descr0", 0, 12, 10), 3),
            new BasketNeed(new Need("id1", "name1", "descr1", 1, 19, 15), 2),
            new BasketNeed(new Need("id2", "name2", "descr2", 2, 6, 6), 1),
        };
        return Arrays.asList(needs);
    }

    //helper function to create a test basket 
    private Basket createTestBasket() {
        // not a test...
        // used to create test baskets

        List<BasketNeed> needs = getTestNeeds();
        Basket basket = new Basket(needs);
        return basket;
    }

    //test that basket is serializable 
    @Test
    public void testSerializeBasket() {}

    //test that basket is deserializable
    @Test
    public void testDeserializeBasket() {}

    //test getting all basket needs when there are no need in the basket 
    @Test
    public void testGetBasketNeedsEmpty() {
        BasketNeed[] needs = {};
        Basket basket = new Basket(Arrays.asList(needs));

        List<BasketNeed> needsList1 = new ArrayList<>();
        List<BasketNeed> needsList2 = basket.getNeeds();
        assertEquals(needsList1, needsList2);
    }

    //test getting all basket needs when there are needs in the basket
    @Test
    public void testGetBasketNeedsHasNeeds() {
        Basket basket = createTestBasket();

        List<BasketNeed> needsList1 = getTestNeeds();
        List<BasketNeed> needsList2 = basket.getNeeds();

        assertEquals(needsList1, needsList2);
    }

    //test getting a basket need, test 0
    @Test
    public void testGetBasketNeed0() {
        Basket basket = createTestBasket();
        Need need = new Need("id0", "name0", "descr0", 0, 0, 0);
        BasketNeed expected = new BasketNeed(need, 3);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    //test getting a basket need, test 1
    @Test
    public void testGetBasketNeed1() {
        Basket basket = createTestBasket();
        Need need = new Need("id1", "name1", "descr1", 1, 1, 1);
        BasketNeed expected = new BasketNeed(need, 2);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    //test getting a basket need, test 2 
    @Test
    public void testGetBasketNeed2() {
        Basket basket = createTestBasket();
        Need need = new Need("id2", "name2", "descr2", 2, 2, 2);
        BasketNeed expected = new BasketNeed(need, 1);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    //test getting a basket need when that need is not found 
    @Test
    public void testGetBasketNeedNotFound() {
        Basket basket = createTestBasket();
        BasketNeed expected = null;
        // null will not be found in the basket
        BasketNeed actual = basket.getBasketNeed(null);

        assertEquals(expected, actual);
    }

    //test has need when the basket does have the need
    @Test
    public void testHasNeedTrue() {
        Basket basket = createTestBasket();
        Need need = basket.getNeeds().get(2).need;

        boolean actual = basket.hasNeed(need);
        
        assertTrue(actual);
    }

    //test has need when the basket does not have the need 
    @Test
    public void testHasNeedFalse() {
        Basket basket = createTestBasket();

        boolean actual = basket.hasNeed(null);
        
        assertFalse(actual);
    }

    // test setting a basket need when no need exists
    @Test
    public void testSetNeed(){
        Basket basket = createTestBasket();

        Need need = new Need("idX", "name0", "descr0", 0, 0, 0);


        BasketNeed actual = basket.setNeed(need);

        assertNull(actual);
    }

    // test that adding a need sorts the basket
    @Test
    public void testAddSorts() {
        Basket basket = new Basket(); // empty
        List<BasketNeed> needs = getTestNeeds();

        basket.updateNeed(needs.get(2).need, 1);
        basket.updateNeed(needs.get(1).need, 2);
        basket.updateNeed(needs.get(0).need, 3);

        List<BasketNeed> actual = basket.getNeeds();

        needs.sort((n2, n1) -> Double.compare(n1.getNeed().getDemandRating(), n2.getNeed().getDemandRating()));

        assertEquals(needs, actual);
    }

    //test adding a need three times 
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

        needs.sort((n2, n1) -> Double.compare(n1.getNeed().getDemandRating(), n2.getNeed().getDemandRating()));

        assertEquals(needs, actual);
    }

    //test updating a need for quantity > 1
    @Test
    public void testUpdate10() {
        Basket basket = new Basket();

        Need need = new Need("id0", "name0", "descr0", 0, 1, 2);

        basket.updateNeed(need, 10);

        BasketNeed expected = new BasketNeed(need, 10);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    //test updating a need for quantity < -1
    @Test
    public void testRemove10() {
        Basket basket = new Basket();

        Need need = new Need("id0", "name0", "descr0", 0, 1, 2);
        
        basket.updateNeed(need, 12);
        basket.updateNeed(need, -10);

        BasketNeed expected = new BasketNeed(need, 2);
        BasketNeed actual = basket.getBasketNeed(need);

        assertEquals(expected, actual);
    }

    //test removing a need into the negatives
    @Test
    public void testRemoveAll() {
        Basket basket = new Basket();

        Need need = new Need("id0", "name0", "descr0", 0, 1, 2);

        basket.addNeed(need);
        basket.updateNeed(need, -5);

        BasketNeed actual = basket.getBasketNeed(need);

        assertNull(actual);
    }

    //test adding a need twice 
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

    //test removing one need when a non one quantity of that need exists
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

    //test removing one need where only one exists
    @Test 
    public void testRemoveNeedOneExists() {
        Basket basket = createTestBasket();
        Need need = basket.getNeeds().get(2).need;

        basket.removeNeed(need);

        BasketNeed actual = basket.getBasketNeed(need);

        assertNull(actual);
    }

    //test removing a basketneed that does not exist 
    @Test 
    public void testRemoveNonExistentNeed() {
        Basket basket = createTestBasket();

        ArrayList<BasketNeed> before = basket.getNeeds();

        basket.removeNeed(null);

        ArrayList<BasketNeed> after = basket.getNeeds();

        assertEquals(before, after);
    }

    //test true equality of two equal basket objects
    @Test
    public void testBasketEqualTrue(){
        Basket basket = createTestBasket();
        Basket basket2 = createTestBasket();
        assertEquals(basket, basket2);
    }

    //test false equality of two unequal basket objects
    @Test
    public void testBasketEqualFalse(){
        Basket basket = createTestBasket();
        Basket basket2 = new Basket();
        assertNotEquals(basket, basket2);
    }

    //test (false) equality of a basket and an object
    @Test
    public void testBasketEqualObjNotBasket(){
        Basket basket = createTestBasket();
        Object basket2 = new Object();
        assertNotEquals(basket, basket2);
    }

    @Test
    public void testBasketCost() {
        Basket basket = createTestBasket();

        double cost = basket.getCost();

        assertEquals(66, cost);

    }
}
