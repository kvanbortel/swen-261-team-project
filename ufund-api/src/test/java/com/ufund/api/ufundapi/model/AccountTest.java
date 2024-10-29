package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private static final String TEST_PASSWORD_HASH = "thisisnotahash";
    
    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
    }

    //Test for creating an account with no basket parameter
    @Test
    public void testConstructorNoBasket() {
        Account newAccount =  new Account(TEST_NAME, TEST_PASSWORD_HASH);
        assertEquals(TEST_NAME, account.getName());
        assertEquals(new Basket(), account.getBasket());
    }

    //Test for creating an account with basket parameter
    @Test
    public void testConstructorBasket() {
        Account newAccount =  new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
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
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        Account account2 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        assertEquals(account1, account2);
    }

    //test for false equality between two accounts depending on name
    @Test
    public void testAccountEqualFalseName() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        Account account2 = new Account("Different Name", TEST_BASKET, TEST_PASSWORD_HASH);
        assertNotEquals(account1, account2);
    }

    //test for false equality between two accounts depending on password hash
    @Test
    public void testAccountEqualFalsePasswordHash() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        Account account2 = new Account(TEST_NAME, TEST_BASKET, "badpass");
        assertNotEquals(account1, account2);
    }

    //test for false equality between two accounts depending on basket
    @Test
    public void testAccountEqualFalseBasket() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        BasketNeed[] needs = {new BasketNeed(new Need("hello", "hello", "hello", 10, 10, 0), 0)};
        Account account2 = new Account(TEST_NAME, new Basket(Arrays.asList(needs)), TEST_PASSWORD_HASH);
        assertNotEquals(account1, account2);
    }

    //test for false equality between an account and an object
    @Test
    public void testAccountEqualObjNotNeed() {
        Account account1 = new Account(TEST_NAME, TEST_BASKET, TEST_PASSWORD_HASH);
        Object account2 = null;
        assertNotEquals(account1, account2);
    }

    @Test 
    public void testGetMoneyFunded() {
        Account account = new Account("NAME", "pass");

        double amount = account.getMoneyFunded();

        assertEquals(0.0, amount);
    }

    @Test 
    public void testGetNeedsFunded() {
        Account account = new Account("NAME", "pass");

        int quantity = account.getNeedsFunded();

        assertEquals(0.0, quantity);
    }

    @Test
    public void testAddMoneyFunded() {
        Account account = new Account("NAME", "pass");

        double addMe = 38.28;

        double newAmount = account.addMoneyFunded(addMe);

        assertEquals(addMe, newAmount);
    }

    @Test
    public void testAddNeedsFunded() {
        Account account = new Account("NAME", "pass");

        int addMe = 7;

        int newQuantity = account.addNeedsFunded(addMe);

        assertEquals(addMe, newQuantity);
    }

    @Test
    public void testOrderAccounts() {
        Account account1 = new Account("account1", "pass");
        Account account2 = new Account("account2", "pass");
        List<Account> accounts= new ArrayList<Account>();
        accounts.add(account1);
        accounts.add(account2);

        List<Account> expected= new ArrayList<Account>();
        expected.add(account2);
        expected.add(account1);

        account1.addMoneyFunded(100);
        account2.addMoneyFunded(15);

        Collections.sort(accounts);

        assertEquals(expected, accounts);
    }

    @Test
    public void testCompareGT() {
        Account account1 = new Account("account1", "pass");
        Account account2 = new Account("account2", "pass");

        account1.addMoneyFunded(100);
        account2.addMoneyFunded(15);
        
        assertEquals(1, account1.compareTo(account2));
    }

    @Test
    public void testCompareEQ() {
        Account account1 = new Account("account1", "pass");
        Account account2 = new Account("account2", "pass");

        account1.addMoneyFunded(15);
        account2.addMoneyFunded(15);
        
        assertEquals(0, account1.compareTo(account2));
    }

    @Test
    public void testCompareLT() {
        Account account1 = new Account("account1", "pass");
        Account account2 = new Account("account2", "pass");

        account1.addMoneyFunded(2);
        account2.addMoneyFunded(15);
        
        assertEquals(-1, account1.compareTo(account2));
    }
}
