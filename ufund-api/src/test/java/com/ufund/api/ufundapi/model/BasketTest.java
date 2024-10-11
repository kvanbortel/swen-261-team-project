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
        BasketNeed[] needs = {
            new BasketNeed("id1", 5),
            new BasketNeed("id7", 1),
            new BasketNeed("id2", 2)
        };
        Basket basket = new Basket(needs);

        ArrayList<BasketNeed> needsList1 = new ArrayList<>();
        ArrayList<BasketNeed> needsList2 = basket.getNeeds();
        needsList1.add(new BasketNeed("id1", 5));
        needsList1.add(new BasketNeed("id7", 1));
        needsList1.add(new BasketNeed("id2", 2));

        assertEquals(needsList1, needsList2);
    }

    @Test
    public void testSearchBasketNeedsOneExists() {}

    @Test
    public void testSearchBasketNeedsManyExists() {}

    @Test
    public void testSearchBasketNeedsNoneExist() {}
    
}
