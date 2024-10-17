package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Account;
import com.ufund.api.ufundapi.model.Basket;
import com.ufund.api.ufundapi.model.BasketNeed;
import com.ufund.api.ufundapi.model.Need;

/**
 * 
 */
@Tag("Persistence-tier")
public class AccountFileDAOTest {

    AccountFileDAO accountFileDAO;
    Account[] testAccounts;
    Basket[] testBaskets;
    Need[] testNeeds;
    BasketNeed[] testBasketNeeds;
    ObjectMapper mockObjectMapper;

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
        // testBasketNeeds[0] = new BasketNeed();

        testBaskets = new Basket[4];        
        testBaskets[0] = new Basket();
        testBaskets[0] = new Basket();
        testBaskets[0] = new Basket();
        testBaskets[0] = new Basket();

        testAccounts = new Account[4];
        testAccounts[0] = new Account("Max");
        testAccounts[1] = new Account("Kayla");
        testAccounts[2] = new Account("Jonah");
        testAccounts[3] = new Account("Ryan");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the need array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Account[].class))
                .thenReturn(testAccounts);
        accountFileDAO = new AccountFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetAccount() throws IOException {
        // Invoke
        Account acc = accountFileDAO.getAccount("Max");

        // Analyze
        assertEquals(testAccounts[0], acc);
    }

    @Test
    public void testGetNeeds() {

    }

    
}
