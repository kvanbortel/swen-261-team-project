package com.ufund.api.ufundapi.model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Class for testing the Account model class
 *
 * @author Max Klot
 */
@Tag("Model-tier")
public class AccountTest {
    private static final String TEST_NAME = "Max";
    private static final Basket TEST_BASKET = new Basket();
    private static final ProfileInfo TEST_PROFILE_INFO = new ProfileInfo();
    private static final Level TEST_LEVEL = Level.NOOB;
    
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
    }

    //Test for creating an account with no basket parameter
    @Test
    public void testConstructorNoBasket() {
        Account newAccount =  new Account(TEST_NAME);
        assertEquals(TEST_NAME, account.getName());
        assertEquals(new Basket(), account.getBasket());
    }

    //Test for creating an account with no basket parameter
    @Test
    public void testConstructorBasket() {
        Account newAccount =  new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        assertEquals(TEST_NAME, account.getName());
        assertEquals(TEST_BASKET, account.getBasket());
    }

    //test for getting name of account
    @Test
    public void testgetName() {
        assertEquals(TEST_NAME, account.getName());
    }

    //test for getting basket of an account 
    @Test
    public void testgetBasket() {
        assertEquals(TEST_BASKET, account.getBasket());
    }

    //test for true equality between two accounts
    @Test
    public void testAccountEqualTrue() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        Account account2 = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        assertEquals(account1, account2);
    }

    //test for false equality between two accounts depending on name
    @Test
    public void testAccountEqualFalseName() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        Account account2 = new Account("Different Name", TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        assertNotEquals(account1, account2);
    }

    //test for false equality between two accounts depending on basket
    @Test
    public void testAccountEqualFalseBasket() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        BasketNeed[] needs = {new BasketNeed(new Need("hello", "hello", "hello", 10, 10, 0), 0)};
        Account account2 = new Account(TEST_NAME, new Basket(Arrays.asList(needs)), TEST_PROFILE_INFO, TEST_LEVEL);
        assertNotEquals(account1, account2);
    }

    //test for false equality between an account and an object
    @Test
    public void testAccountEqualObjNotNeed() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PROFILE_INFO, TEST_LEVEL);
        Object account2 = null;
        assertNotEquals(account1, account2);
    }
}
