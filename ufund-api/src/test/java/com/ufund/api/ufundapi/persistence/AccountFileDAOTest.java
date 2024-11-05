package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ufund.api.ufundapi.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {
    private static final ProfileInfo TEST_PROFILE_INFO = new ProfileInfo();
    private static final Level TEST_LEVEL = Level.NOOB;

    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    Basket[] testBaskets;
    Need[] testNeeds;
    BasketNeed[] testBasketNeeds;
    ObjectMapper mockObjectMapper;
    NeedDAO mockNeedDAO;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupAccountFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testNeeds = new Need[3];
        testNeeds[0] = new Need("MOCKID-0","NeedA1", "Description1", 0, 3, 12.5);
        testNeeds[1] = new Need("MOCKID-1","NeedB1", "Description2", 2.9, 10, 5.4);
        testNeeds[2] = new Need("MOCKID-2","NeedB2", "Description3", 1.3, 6, 12.32);

        testBasketNeeds = new BasketNeed[5];
        testBasketNeeds[0] = new BasketNeed(testNeeds[0], 1);
        testBasketNeeds[1] = new BasketNeed(testNeeds[1], 1);
        testBasketNeeds[2] = new BasketNeed(testNeeds[2], 1);
        testBasketNeeds[3] = new BasketNeed(testNeeds[1], 3);
        testBasketNeeds[4] = new BasketNeed(testNeeds[2], 6);

        testBaskets = new Basket[4];        

        testBaskets[0] = new Basket(); // empty

        testBaskets[1] = new Basket(); // need0: 1, need3: 3
        testBaskets[1].addNeed(testNeeds[0]);
        testBaskets[1].addNeed(testNeeds[1]);
        testBaskets[1].addNeed(testNeeds[1]);
        testBaskets[1].addNeed(testNeeds[1]);

        testBaskets[2] = new Basket(); // need4: 6
        for (int i=0; i<6; i++) {
            testBaskets[2].addNeed(testNeeds[2]);
        }

        testBaskets[3] = new Basket(); // need0: 1
        testBaskets[3].addNeed(testNeeds[0]);

        // accounts baskets match the related index in testBaskets
        testAccounts = new Account[4];
        testAccounts[0] = new Account("Max");
        testAccounts[1] = new Account("Kayla", testBaskets[1], TEST_PROFILE_INFO, TEST_LEVEL);
        testAccounts[2] = new Account("Jonah", testBaskets[2], TEST_PROFILE_INFO, TEST_LEVEL);
        testAccounts[3] = new Account("Ryan", testBaskets[3], TEST_PROFILE_INFO, TEST_LEVEL);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(testAccounts);

        mockNeedDAO = mock(NeedDAO.class);

        for(Need n: testNeeds){
            when(mockNeedDAO.getNeed(n.getId())).thenReturn(n);
        }

        accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper, mockNeedDAO);
    }

    @Test
    public void testGetAccount() throws IOException {
        // Invoke
        Account acc = accountFileDAO.getAccount("Max");

        // Analyze
        assertEquals(testAccounts[0], acc);
    }

    @Test
    public void testGetNeeds() throws IOException {
        // Invoke
        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertEquals(testBaskets[1].getNeeds(), needs);
    }

    @Test
    public void testGetNeedsNullAccount() throws IOException {
        // Invoke
        List<BasketNeed> needs = accountFileDAO.getNeeds("idx");

        assertNull(needs);
    }

    @Test
    public void testRemoveNullNeed() throws IOException {
        // Setup

        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(null);

        // Invoke

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertTrue(needs.size() == 1);
    }

    @Test
    public void testDropDecreasedNeed() throws IOException {
        // Setup
        Need decreasedNeed = new Need("MOCKID-1","NeedB1", "Description2", 2.9, 1, 5.4);

        when(mockNeedDAO.getNeed("MOCKID-1")).thenReturn(decreasedNeed);

        // Invoke

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        for(BasketNeed n: needs){
            if(n.getNeed().getId() == decreasedNeed.getId()){
                assertEquals(decreasedNeed, n.getNeed());
                assertEquals(n.getQuantity(), 1);
            }
        }
    }

    @Test 
    public void testCheckout() throws IOException {
        // Setup to fufill all of one need
        testBaskets[1].updateNeed(testNeeds[0], 3 - testBaskets[1].getBasketNeed(testNeeds[0]).getQuantity());
        
        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(testNeeds[0]);
        when(mockNeedDAO.deleteNeed(testNeeds[0].getId())).thenReturn(true);

        // Invoke

        boolean checked = accountFileDAO.checkout("Kayla");

        List<BasketNeed> needs = accountFileDAO.getNeeds("Kayla");

        assertTrue(checked);
        assertEquals(needs.size(), 0);
    }

    @Test 
    public void testCheckoutNullAccount() throws IOException {
        // Invoke

        boolean checkedout = accountFileDAO.checkout("idx");

        assertTrue(!checkedout);
    }

    @Test 
    public void testCheckoutLowNeed() throws IOException {
        // Setup 
        testBaskets[1].updateNeed(testNeeds[0], 5 - testBaskets[1].getBasketNeed(testNeeds[0]).getQuantity());

        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(testNeeds[0]);
        when(mockNeedDAO.deleteNeed(testNeeds[0].getId())).thenReturn(true);

        // Invoke

        boolean checkedout = accountFileDAO.checkout("Kayla");

        assertTrue(!checkedout);
    }

    @Test 
    public void testCheckoutNullNeed() throws IOException {
        // Setup 
        when(mockNeedDAO.getNeed(testNeeds[0].getId())).thenReturn(null);

        // Invoke

        boolean checkedout = accountFileDAO.checkout("Kayla");

        assertTrue(!checkedout);
    }

    @Test
    public void testCreateAccount() throws IOException {
        Account created = accountFileDAO.createAccount("Bichael");
        Account gotten = accountFileDAO.getAccount("Bichael");

        assertEquals(created, gotten);
    }

    @Test
    public void testCreateExistingAccount() throws IOException {
        Account created = accountFileDAO.createAccount("Max");

        assertNull(created);
    }

    @Test
    public void testAddNeed() throws IOException {
        BasketNeed ryanNeed = accountFileDAO.updateNeed("Ryan", testNeeds[0], 1);

        assertEquals(ryanNeed.getQuantity(), 2);
    }

    @Test
    public void testRemoveNeed() throws IOException {
        BasketNeed kaylaNeed = accountFileDAO.updateNeed("Kayla", testNeeds[1], -1);

        assertEquals(2, kaylaNeed.getQuantity());
    }

    @Test
    public void testUpdateNeedForFakeAccount() throws IOException {
        assertNull(accountFileDAO.updateNeed("FAKE", testNeeds[1], 1));
    }

    
}
