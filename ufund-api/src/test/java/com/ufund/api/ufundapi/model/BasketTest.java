package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

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
        BasketNeed[] needs = {
            new BasketNeed("id0", 3),
            new BasketNeed("id1", 2),
            new BasketNeed("id2", 1)
        };
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
        Basket basket = new Basket(needs);

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

    public void getBasketNeed0() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id0", 3);
        BasketNeed actual = basket.getBasketNeed("id0");

        assertEquals(expected, actual);
    }

    public void getBasketNeed1() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id1", 2);
        BasketNeed actual = basket.getBasketNeed("id1");

        assertEquals(expected, actual);
    }

    public void getBasketNeed2() {
        Basket basket = createBasket();
        BasketNeed expected = new BasketNeed("id2", 1);
        BasketNeed actual = basket.getBasketNeed("id2");

        assertEquals(expected, actual);
    }
    
}
